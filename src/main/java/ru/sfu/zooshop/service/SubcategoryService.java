package ru.sfu.zooshop.service;

import org.springframework.data.domain.Pageable;
import ru.sfu.zooshop.dto.response.open.subcategory.RichSubcategoryResponse;
import ru.sfu.zooshop.entity.CategoryEntity;
import ru.sfu.zooshop.entity.SubcategoryEntity;

import java.util.List;

public interface SubcategoryService {
  List<SubcategoryEntity> getSubcategoriesByParent(CategoryEntity parent);
  RichSubcategoryResponse getSubcategoryBySlug(String categorySlug, String subcategorySlug, Pageable pageable);
}
