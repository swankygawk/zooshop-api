package ru.sfu.zooshop.service.admin;

import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.category.CategoryRequest;

public interface AdminCategoryService {
  Long createCategory(CategoryRequest request);
  void updateCategory(Long id, CategoryRequest request);
  void updateCategoryPicture(Long id, MultipartFile file);
  void deleteCategoryPicture(Long id);
}
