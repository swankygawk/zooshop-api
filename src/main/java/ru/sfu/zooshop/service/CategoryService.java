package ru.sfu.zooshop.service;

import ru.sfu.zooshop.dto.response.open.category.AllCategoriesResponse;
import ru.sfu.zooshop.dto.response.open.category.RichCategoryResponse;
import ru.sfu.zooshop.entity.CategoryEntity;

public interface CategoryService {
  CategoryEntity findCategoryById(Long id);
  CategoryEntity findCategoryBySlug(String slug);
  AllCategoriesResponse getCategories();
  RichCategoryResponse getCategoryById(Long id);
  RichCategoryResponse getCategoryBySlug(String slug);
}
