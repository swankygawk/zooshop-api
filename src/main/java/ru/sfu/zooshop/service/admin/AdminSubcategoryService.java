package ru.sfu.zooshop.service.admin;

import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.subcategory.SubcategoryRequest;

public interface AdminSubcategoryService {
  Long createSubcategory(SubcategoryRequest request);
  void updateSubcategory(Long id, SubcategoryRequest request);
  void updateSubcategoryPicture(Long id, MultipartFile file);
  void deleteSubcategoryPicture(Long id);
}
