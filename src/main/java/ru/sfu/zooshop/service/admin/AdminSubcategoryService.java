package ru.sfu.zooshop.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.subcategory.SubcategoryRequest;
import ru.sfu.zooshop.dto.request.admin.subcategory.SubcategoryUpdateRequest;
import ru.sfu.zooshop.dto.response.admin.subcategory.AdminRichSubcategoryResponse;
import ru.sfu.zooshop.entity.SubcategoryEntity;

public interface AdminSubcategoryService {
  SubcategoryEntity findSubcategoryById(Long id);
  AdminRichSubcategoryResponse getSubcategory(String categorySlug, String subcategorySlug, Pageable pageable);
  String createSubcategory(String categorySlug, SubcategoryRequest request);
  void updateSubcategory(String categorySlug, String subcategorySlug, SubcategoryUpdateRequest request);
  void toggleSubcategoryVisibility(String categorySlug, String subcategorySlug, boolean hidden);
  void updateSubcategoryPicture(String categorySlug, String subcategorySlug, MultipartFile file);
  void deleteSubcategoryPicture(String categorySlug, String subcategorySlug);
}
