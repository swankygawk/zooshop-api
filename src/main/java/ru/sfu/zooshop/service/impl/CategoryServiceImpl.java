package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.dto.response.open.category.AllCategoriesResponse;
import ru.sfu.zooshop.dto.response.open.category.RichCategoryResponse;
import ru.sfu.zooshop.entity.CategoryEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.category.CategoryMapper;
import ru.sfu.zooshop.repository.CategoryRepository;
import ru.sfu.zooshop.repository.SubcategoryRepository;
import ru.sfu.zooshop.service.CategoryService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final SubcategoryRepository subcategoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public CategoryEntity findCategoryById(Long id) {
    return categoryRepository.findById(id)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Category with id " + id + " not found"));
  }

  @Override
  public CategoryEntity findCategoryBySlug(String slug) {
    return categoryRepository.findBySlug(slug)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Category with slug " + slug + " not found"));
  }

  @Override
  public AllCategoriesResponse getCategories() {
    List<CategoryEntity> categories = categoryRepository.findAll();
    return new AllCategoriesResponse(
      categories.stream()
        .map(categoryMapper::categoryEntityToBasicCategoryResponse)
        .toList()
    );
  }

  @Override
  public RichCategoryResponse getCategoryById(Long id) {
    CategoryEntity category = findCategoryById(id);
    return categoryMapper.categoryEntityToRichCategoryResponse(category);
  }

  @Override
  public RichCategoryResponse getCategoryBySlug(String slug) {
    CategoryEntity category = findCategoryBySlug(slug);
    return categoryMapper.categoryEntityToRichCategoryResponse(category);
  }


}
