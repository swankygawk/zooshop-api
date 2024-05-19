package ru.sfu.zooshop.service;

import org.springframework.data.domain.Pageable;
import ru.sfu.zooshop.dto.response.open.category.AllCategoriesResponse;
import ru.sfu.zooshop.dto.response.open.category.RichCategoryResponse;

public interface CategoryService {
  AllCategoriesResponse getCategories();
  RichCategoryResponse getCategoryBySlug(String slug, Pageable pageable);
}
