package ru.sfu.zooshop.service.impl.admin;

import com.github.slugify.Slugify;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.category.CategoryRequest;
import ru.sfu.zooshop.dto.response.admin.category.AdminAllCategoriesResponse;
import ru.sfu.zooshop.dto.response.admin.category.AdminBasicCategoryResponse;
import ru.sfu.zooshop.dto.response.admin.category.AdminRichCategoryResponse;
import ru.sfu.zooshop.dto.response.admin.product.AdminBasicProductResponse;
import ru.sfu.zooshop.entity.CategoryEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.AdminMapper;
import ru.sfu.zooshop.repository.CategoryRepository;
import ru.sfu.zooshop.repository.ProductRepository;
import ru.sfu.zooshop.service.FileService;
import ru.sfu.zooshop.service.admin.AdminCategoryService;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;
import static ru.sfu.zooshop.constant.Constant.CATEGORY_PICTURE_STORAGE_LOCATION;
import static ru.sfu.zooshop.enumeration.FileType.CATEGORY_PICTURE;
import static ru.sfu.zooshop.utility.SystemUtility.convert;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class AdminCategoryServiceImpl implements AdminCategoryService {
  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private final FileService fileService;
  private final AdminMapper adminMapper;
  private final Slugify slugifier;

  private void validateName(String name) {
    if (categoryRepository.existsByName(name)) throw new ApiException(CONFLICT, "Category with name " + name + " already exists");
  }

  private Page<AdminBasicProductResponse> getProductsByCategory(CategoryEntity category, Pageable pageable) {
    return productRepository.findAllByCategory(category, pageable)
      .map(adminMapper::productEntityToAdminBasicProductResponse);
  }

  @Override
  public CategoryEntity findCategoryBySlug(String slug) {
    return categoryRepository.findBySlug(slug)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Category with slug " + slug + " not found"));
  }

  @Override
  public CategoryEntity findCategoryById(Long id) {
    return categoryRepository.findById(id)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Category with id " + id + " not found"));
  }

  @Override
  public AdminAllCategoriesResponse getCategories() {
    List<AdminBasicCategoryResponse> categories = categoryRepository.findAll().stream()
      .map(adminMapper::categoryEntityToAdminBasicCategoryResponse)
      .toList();
    return new AdminAllCategoriesResponse(categories);
  }

  @Override
  public AdminRichCategoryResponse getCategory(String slug, Pageable pageable) {
    CategoryEntity categoryEntity = findCategoryBySlug(slug);
    Page<AdminBasicProductResponse> products = getProductsByCategory(categoryEntity, pageable);
    AdminRichCategoryResponse category = adminMapper.categoryEntityToAdminRichCategoryResponse(categoryEntity);
    category.setProducts(products);
    return category;
  }

  @Override
  public String createCategory(CategoryRequest request) {
    String name = convert(request.getName());
    validateName(name);
    CategoryEntity category = categoryRepository.save(
      new CategoryEntity(
        name,
        slugifier.slugify(name)
      )
    );
    return category.getSlug();
  }

  @Override
  public void updateCategory(String slug, CategoryRequest request) {
    CategoryEntity category = findCategoryBySlug(slug);
    String name = convert(request.getName());
    if (!Objects.equals(category.getName(), name)) {
      validateName(request.getName());
    }
    category.setName(name);
    category.setSlug(slugifier.slugify(name));
    categoryRepository.save(category);
  }

  @Override
  public void toggleCategoryVisibility(String slug, boolean hidden) {
    CategoryEntity category = findCategoryBySlug(slug);
    if (category.isHidden() == hidden) throw new ApiException(CONFLICT, "Category is already " + (hidden ? "hidden" : "visible"));
    category.setHidden(hidden);
    category.getChildren().forEach(
      subcategory -> subcategory.setHidden(hidden)
    );
    category.getProducts().forEach(
      product -> product.setHidden(hidden)
    );
    categoryRepository.save(category);
  }

  @Override
  public void updateCategoryPicture(String slug, MultipartFile file) {
    CategoryEntity category = findCategoryBySlug(slug);
    String fileName = category.getReferenceId() + ".png";
    String pictureUrl = fileService.saveFile(file, CATEGORY_PICTURE_STORAGE_LOCATION, fileName, CATEGORY_PICTURE);
    if (category.getPictureUrl() == null) {
      category.setPictureUrl(pictureUrl);
      categoryRepository.save(category);
    }
  }

  @Override
  public void deleteCategoryPicture(String slug) {
    CategoryEntity category = findCategoryBySlug(slug);
    if (category.getPictureUrl() == null) throw new ApiException(BAD_REQUEST, "Category does not have a productpicture");
    category.setPictureUrl(null);
    categoryRepository.save(category);
    fileService.deleteFile(CATEGORY_PICTURE_STORAGE_LOCATION, category.getReferenceId() + ".png");
  }
}
