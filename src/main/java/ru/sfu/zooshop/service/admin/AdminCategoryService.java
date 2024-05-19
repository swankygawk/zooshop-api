package ru.sfu.zooshop.service.admin;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.category.CategoryRequest;
import ru.sfu.zooshop.dto.response.admin.category.AdminAllCategoriesResponse;
import ru.sfu.zooshop.dto.response.admin.category.AdminRichCategoryResponse;
import ru.sfu.zooshop.entity.CategoryEntity;

public interface AdminCategoryService {
  CategoryEntity findCategoryById(Long id);
  CategoryEntity findCategoryBySlug(String slug);
  AdminAllCategoriesResponse getCategories();
  AdminRichCategoryResponse getCategory(String slug, Pageable pageable);
  String createCategory(CategoryRequest request);
  void updateCategory(String slug, CategoryRequest request);
  void toggleCategoryVisibility(String slug, boolean hidden);
  void updateCategoryPicture(String slug, MultipartFile file);
  void deleteCategoryPicture(String slug);
}
