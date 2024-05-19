package ru.sfu.zooshop.service.impl.admin;

import com.github.slugify.Slugify;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.subcategory.SubcategoryRequest;
import ru.sfu.zooshop.dto.request.admin.subcategory.SubcategoryUpdateRequest;
import ru.sfu.zooshop.dto.response.admin.product.AdminBasicProductResponse;
import ru.sfu.zooshop.dto.response.admin.subcategory.AdminRichSubcategoryResponse;
import ru.sfu.zooshop.entity.CategoryEntity;
import ru.sfu.zooshop.entity.SubcategoryEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.AdminMapper;
import ru.sfu.zooshop.repository.CategoryRepository;
import ru.sfu.zooshop.repository.ProductRepository;
import ru.sfu.zooshop.repository.SubcategoryRepository;
import ru.sfu.zooshop.service.FileService;
import ru.sfu.zooshop.service.admin.AdminCategoryService;
import ru.sfu.zooshop.service.admin.AdminSubcategoryService;

import java.util.Objects;

import static org.springframework.http.HttpStatus.*;
import static ru.sfu.zooshop.constant.Constant.SUBCATEGORY_PICTURE_STORAGE_LOCATION;
import static ru.sfu.zooshop.enumeration.FileType.SUBCATEGORY_PICTURE;
import static ru.sfu.zooshop.utility.SystemUtility.convert;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class AdminSubcategoryServiceImpl implements AdminSubcategoryService {
  private final CategoryRepository categoryRepository;
  private final SubcategoryRepository subcategoryRepository;
  private final ProductRepository productRepository;
  private final AdminCategoryService adminCategoryService;
  private final FileService fileService;
  private final Slugify slugifier;
  private final AdminMapper adminMapper;

  private SubcategoryEntity findSubcategoryBySlug(String categorySlug, String subcategorySlug) {
    return subcategoryRepository.findByParentSlugAndSlug(categorySlug, subcategorySlug)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Subcategory with category slug " + categorySlug + " and slug " + subcategorySlug + " not found"));
  }

  private void validateName(CategoryEntity parent, String name) {
    if (categoryRepository.existsByName(name)) throw new ApiException(CONFLICT, "Subcategory cannot have the same name (" + name + ") as one of the categories");
    if (subcategoryRepository.existsByParentAndName(parent, name)) throw new ApiException(CONFLICT, "Subcategory with parent category name " + parent.getName() + " and name " + name + " already exists");
  }

  private Page<AdminBasicProductResponse> getProductsBySubcategory(SubcategoryEntity subcategory, Pageable pageable) {
    return productRepository.findAllBySubcategory(subcategory, pageable)
      .map(adminMapper::productEntityToAdminBasicProductResponse);
  }

  @Override
  public SubcategoryEntity findSubcategoryById(Long id) {
    return subcategoryRepository.findById(id)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Subcategory with id " + id + " not found"));
  }

  @Override
  public AdminRichSubcategoryResponse getSubcategory(String categorySlug, String subcategorySlug, Pageable pageable) {
    SubcategoryEntity subcategoryEntity = findSubcategoryBySlug(categorySlug, subcategorySlug);
    Page<AdminBasicProductResponse> products = getProductsBySubcategory(subcategoryEntity, pageable);
    AdminRichSubcategoryResponse subcategory = adminMapper.subcategoryEntityToAdminRichSubcategoryResponse(subcategoryEntity);
    subcategory.setProducts(products);
    return subcategory;
  }

  @Override
  public String createSubcategory(String categorySlug, SubcategoryRequest request) {
    CategoryEntity parent = adminCategoryService.findCategoryBySlug(categorySlug);
    String name = convert(request.getName());
    validateName(parent, name);
    SubcategoryEntity subcategory = new SubcategoryEntity(
      parent,
      name,
      slugifier.slugify(name)
    );
    subcategoryRepository.save(subcategory);
    return subcategory.getSlug();
  }

  @Override
  public void updateSubcategory(String categorySlug, String subcategorySlug, SubcategoryUpdateRequest request) {
    SubcategoryEntity subcategory = findSubcategoryBySlug(categorySlug, subcategorySlug);
    CategoryEntity parent = adminCategoryService.findCategoryById(request.getParentId());
    String name = convert(request.getName());
    if (
      !Objects.equals(subcategory.getParent().getId(), request.getParentId())
      || !Objects.equals(subcategory.getName(), name)
    ) {
      validateName(parent, name);
    }
    subcategory.setParent(parent);
    subcategory.setName(name);
    subcategory.setSlug(slugifier.slugify(name));
    subcategory.setHidden(parent.isHidden());
    subcategoryRepository.save(subcategory);
  }

  @Override
  public void toggleSubcategoryVisibility(String categorySlug, String subcategorySlug, boolean hidden) {
    SubcategoryEntity subcategory = findSubcategoryBySlug(categorySlug, subcategorySlug);
    if (subcategory.getParent().isHidden() && !hidden) throw new ApiException(CONFLICT, "Cannot show subcategory with hidden parent category");
    if (subcategory.isHidden() == hidden) throw new ApiException(CONFLICT, "Subcategory is already " + (hidden ? "hidden" : "visible"));
    subcategory.setHidden(hidden);
    subcategory.getProducts().forEach(
      product -> product.setHidden(hidden)
    );
    subcategoryRepository.save(subcategory);
  }

  @Override
  public void updateSubcategoryPicture(String categorySlug, String subcategorySlug, MultipartFile file) {
    SubcategoryEntity subcategory = findSubcategoryBySlug(categorySlug, subcategorySlug);
    String fileName = subcategory.getReferenceId() + ".png";
    String pictureUrl = fileService.saveFile(file, SUBCATEGORY_PICTURE_STORAGE_LOCATION, fileName, SUBCATEGORY_PICTURE);
    if (subcategory.getPictureUrl() == null) {
      subcategory.setPictureUrl(pictureUrl);
      subcategoryRepository.save(subcategory);
    }
  }

  @Override
  public void deleteSubcategoryPicture(String categorySlug, String subcategorySlug) {
    SubcategoryEntity subcategory = findSubcategoryBySlug(categorySlug, subcategorySlug);
    if (subcategory.getPictureUrl() == null) throw new ApiException(BAD_REQUEST, "Subcategory does not have a productpicture");
    subcategory.setPictureUrl(null);
    subcategoryRepository.save(subcategory);
    fileService.deleteFile(SUBCATEGORY_PICTURE_STORAGE_LOCATION, subcategory.getReferenceId() + ".png");
  }
}
