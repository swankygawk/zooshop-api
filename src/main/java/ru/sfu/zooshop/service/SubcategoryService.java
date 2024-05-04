package ru.sfu.zooshop.service;

import ru.sfu.zooshop.entity.SubcategoryEntity;

import java.util.List;

public interface SubcategoryService {
  SubcategoryEntity findSubcategoryById(Long id);
  SubcategoryEntity findSubcategoryBySlug(String slug);
  List<SubcategoryEntity> getSubcategoriesByParentId(Long parentId);
  RichSubcategoryResponse getSubcategoryById(Long id);
  RichSubcategoryResponse getCategoryBySlug(String slug);
}
