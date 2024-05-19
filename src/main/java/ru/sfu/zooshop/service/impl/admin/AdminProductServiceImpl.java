package ru.sfu.zooshop.service.impl.admin;

import com.github.slugify.Slugify;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.product.ProductRequest;
import ru.sfu.zooshop.dto.request.admin.product.SupplyRequest;
import ru.sfu.zooshop.dto.request.admin.productpicture.ProductPictureOrderRequest;
import ru.sfu.zooshop.dto.response.admin.product.AdminAllProductsResponse;
import ru.sfu.zooshop.dto.response.admin.product.AdminBasicProductResponse;
import ru.sfu.zooshop.dto.response.admin.product.AdminRichProductResponse;
import ru.sfu.zooshop.entity.CategoryEntity;
import ru.sfu.zooshop.entity.ProductEntity;
import ru.sfu.zooshop.entity.ProductPictureEntity;
import ru.sfu.zooshop.entity.SubcategoryEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.AdminMapper;
import ru.sfu.zooshop.repository.ProductPictureRepository;
import ru.sfu.zooshop.repository.ProductRepository;
import ru.sfu.zooshop.service.FileService;
import ru.sfu.zooshop.service.admin.AdminCategoryService;
import ru.sfu.zooshop.service.admin.AdminProductService;
import ru.sfu.zooshop.service.admin.AdminSubcategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.http.HttpStatus.*;
import static ru.sfu.zooshop.constant.Constant.PRODUCT_PICTURE_STORAGE_LOCATION;
import static ru.sfu.zooshop.enumeration.FileType.PRODUCT_PICTURE;
import static ru.sfu.zooshop.utility.SystemUtility.convert;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class AdminProductServiceImpl implements AdminProductService {
  private final ProductRepository productRepository;
  private final ProductPictureRepository productPictureRepository;
  private final AdminCategoryService adminCategoryService;
  private final AdminSubcategoryService adminSubcategoryService;
  private final FileService fileService;
  private final AdminMapper adminMapper;
  private final Slugify slugifier;

  private ProductEntity findProductBySlug(String slug) {
    return productRepository.findBySlug(slug)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Product with slug " + slug + " not found"));
  }

  private void validateName(String name) {
    if (productRepository.existsByName(name)) throw new ApiException(CONFLICT, "Product with name " + name + " already exists");
  }

  private ProductPictureEntity findProductPictureById(Long id, ProductEntity product) {
    return productPictureRepository.findByIdAndProduct(id, product)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Product picture with ID " + id + " not found"));
  }

  @Override
  public AdminAllProductsResponse getProducts(Pageable pageable) {
    Page<AdminBasicProductResponse> products = productRepository.findAll(pageable)
      .map(adminMapper::productEntityToAdminBasicProductResponse);
    return new AdminAllProductsResponse(products);
  }

  @Override
  public AdminRichProductResponse getProduct(String slug) {
    ProductEntity product = findProductBySlug(slug);
    return adminMapper.productEntityToAdminRichProductResponse(product);
  }

  @Override
  public String createProduct(ProductRequest request) {
    CategoryEntity category = adminCategoryService.findCategoryById(request.getCategoryId());
    SubcategoryEntity subcategory = adminSubcategoryService.findSubcategoryById(request.getSubcategoryId());
    String name = convert(request.getName());
    validateName(name);
    ProductEntity product = new ProductEntity(
      category,
      subcategory,
      name,
      slugifier.slugify(name),
      request.getDescription(),
      request.getPrice()
    );
    productRepository.save(product);
    return product.getSlug();
  }

  @Override
  public void updateProduct(String slug, ProductRequest request) {
    ProductEntity product = findProductBySlug(slug);
    CategoryEntity category = adminCategoryService.findCategoryById(request.getCategoryId());
    SubcategoryEntity subcategory = adminSubcategoryService.findSubcategoryById(request.getSubcategoryId());
    String name = convert(request.getName());
    if (!Objects.equals(product.getName(), name)) {
      validateName(name);
    }
    product.setCategory(category);
    product.setSubcategory(subcategory);
    product.setName(name);
    product.setSlug(slugifier.slugify(name));
    product.setDescription(request.getDescription());
    product.setPrice(request.getPrice());
    product.setHidden(subcategory.isHidden());
    productRepository.save(product);
  }

  @Override
  public void toggleProductVisibility(String slug, boolean hidden) {
    ProductEntity product = findProductBySlug(slug);
    if (product.getSubcategory().isHidden() && !hidden) throw new ApiException(CONFLICT, "Cannot show product with hidden subcategory");
    if (product.isHidden() == hidden) throw new ApiException(CONFLICT, "Product is already " + (hidden ? "hidden" : "visible"));
    product.setHidden(hidden);
    productRepository.save(product);
  }

  @Override
  public void uploadProductPictures(String slug, List<MultipartFile> files) {
    ProductEntity product = findProductBySlug(slug);
    AtomicReference<Integer> position = new AtomicReference<>(productPictureRepository.countByProduct(product));
    List<ProductPictureEntity> pictures = files.stream()
      .map(
        file -> {
          ProductPictureEntity picture = new ProductPictureEntity();
          String fileName = picture.getReferenceId()+ ".png";
          String url = fileService.saveFile(file, PRODUCT_PICTURE_STORAGE_LOCATION, fileName, PRODUCT_PICTURE);
          picture.setProduct(product);
          picture.setUrl(url);
          picture.setPosition(position.getAndSet((position.get() + 1)));
          return picture;
        }
      )
      .toList();
    productPictureRepository.saveAll(pictures);
  }

  @Override
  public void deleteProductPicture(String slug, Long id) {
    ProductEntity product = findProductBySlug(slug);
    ProductPictureEntity pictureToDelete = findProductPictureById(id, product);
    List<ProductPictureEntity> pictures = productPictureRepository.findAllByProductAndPositionGreaterThanOrderByPositionAsc(product, pictureToDelete.getPosition());
    for (ProductPictureEntity picture : pictures) {
      picture.setPosition(picture.getPosition() - 1);
    }
    fileService.deleteFile(PRODUCT_PICTURE_STORAGE_LOCATION, pictureToDelete.getReferenceId() + ".png");
    productPictureRepository.delete(pictureToDelete);
    productPictureRepository.saveAll(pictures);
  }

  @Override
  public void updatePictureOrder(String slug, ProductPictureOrderRequest request) {
    ProductEntity product = findProductBySlug(slug);
    if (!Objects.equals(
      request.getIds().size(),
      productPictureRepository.countByProduct(product))
    ) throw new ApiException(BAD_REQUEST, "IDs list size is not equal to product pictures number");
    List<ProductPictureEntity> pictures = new ArrayList<>();
    int position = 0;
    for (Long id : request.getIds()) {
      ProductPictureEntity picture = findProductPictureById(id, product);
      picture.setPosition(position++);
      pictures.add(picture);
    }
    productPictureRepository.saveAll(pictures);
  }


  @Override
  public void addProductSupply(String slug, SupplyRequest request) {
    ProductEntity product = findProductBySlug(slug);
    product.setQuantityInStock(
      product.getQuantityInStock() + request.getAmount()
    );
    productRepository.save(product);
  }

  @Override
  public void removeProductSupply(String slug, SupplyRequest request) {
    ProductEntity product = findProductBySlug(slug);
    if (request.getAmount() > product.getQuantityInStock()) throw new ApiException(BAD_REQUEST, "Cannot remove more than there is");
    product.setQuantityInStock(
      product.getQuantityInStock() - request.getAmount()
    );
    productRepository.save(product);
  }
}
