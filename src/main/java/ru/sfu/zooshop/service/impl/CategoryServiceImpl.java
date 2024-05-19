package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.dto.response.open.category.AllCategoriesResponse;
import ru.sfu.zooshop.dto.response.open.category.RichCategoryResponse;
import ru.sfu.zooshop.dto.response.open.product.BasicProductResponse;
import ru.sfu.zooshop.entity.CategoryEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.OpenMapper;
import ru.sfu.zooshop.repository.CategoryRepository;
import ru.sfu.zooshop.repository.ProductRepository;
import ru.sfu.zooshop.service.CategoryService;
import ru.sfu.zooshop.service.SubcategoryService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final ProductRepository productRepository;
  private final SubcategoryService subcategoryService;
  private final OpenMapper openMapper;

  private CategoryEntity findCategoryBySlug(String slug) {
    return categoryRepository.findBySlugAndHiddenFalse(slug)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Category with slug " + slug + " not found"));
  }

  private Page<BasicProductResponse> getProductsByCategory(CategoryEntity category, Pageable pageable) {
    return productRepository.findAllByCategoryAndHiddenFalse(category, pageable)
      .map(openMapper::productEntityToBasicProductResponse);
  }

  @Override
  public AllCategoriesResponse getCategories() {
    List<CategoryEntity> categories = categoryRepository.findAllByHiddenFalse();
    return new AllCategoriesResponse(
      categories.stream()
        .map(openMapper::categoryEntityToBasicCategoryResponse)
        .toList()
    );
  }

  @Override
  public RichCategoryResponse getCategoryBySlug(String slug, Pageable pageable) {
    CategoryEntity categoryEntity = findCategoryBySlug(slug);
    categoryEntity.setChildren(subcategoryService.getSubcategoriesByParent(categoryEntity));
    Page<BasicProductResponse> products = getProductsByCategory(categoryEntity, pageable);
    RichCategoryResponse category = openMapper.categoryEntityToRichCategoryResponse(categoryEntity);
    category.setProducts(products);
    return category;
  }
}
