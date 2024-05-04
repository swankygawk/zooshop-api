package ru.sfu.zooshop.service.impl.admin;

import com.github.slugify.Slugify;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.subcategory.SubcategoryRequest;
import ru.sfu.zooshop.entity.CategoryEntity;
import ru.sfu.zooshop.entity.SubcategoryEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.repository.SubcategoryRepository;
import ru.sfu.zooshop.service.CategoryService;
import ru.sfu.zooshop.service.FileService;
import ru.sfu.zooshop.service.SubcategoryService;
import ru.sfu.zooshop.service.admin.AdminSubcategoryService;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static ru.sfu.zooshop.constant.Constant.SUBCATEGORY_PICTURE_STORAGE_LOCATION;
import static ru.sfu.zooshop.enumeration.FileType.SUBCATEGORY_PICTURE;
import static ru.sfu.zooshop.utility.SystemUtility.convert;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class AdminSubcategoryServiceImpl implements AdminSubcategoryService {
  private final SubcategoryRepository subcategoryRepository;
  private final CategoryService categoryService;
  private final SubcategoryService subcategoryService;
  private final Slugify slugifier;
  private final FileService fileService;

  private void validateName(String name) {
    if (subcategoryRepository.existsByName(name)) {
      throw new ApiException(CONFLICT, "Subcategory with name " + name + " already exists");
    }
  }

  @Override
  public Long createSubcategory(SubcategoryRequest request) {
    CategoryEntity parent = categoryService.findCategoryById(request.getParentId());
    String name = convert(request.getName());
    validateName(name);
    SubcategoryEntity subcategory = subcategoryRepository.save(
      new SubcategoryEntity(
        parent,
        name,
        slugifier.slugify(name)
      )
    );
    return subcategory.getId();
  }

  @Override
  public void updateSubcategory(Long id, SubcategoryRequest request) {
    SubcategoryEntity subcategory = subcategoryService.findSubcategoryById(id);
    CategoryEntity parent = categoryService.findCategoryById(request.getParentId());
    String name = convert(request.getName());
    if (!Objects.equals(subcategory.getName(), name)) {
      validateName(name);
    }
    subcategory.setParent(parent);
    subcategory.setName(name);
    subcategory.setSlug(slugifier.slugify(name));
    subcategoryRepository.save(subcategory);
  }

  @Override
  public void updateSubcategoryPicture(Long id, MultipartFile file) {
    SubcategoryEntity subcategory = subcategoryService.findSubcategoryById(id);
    String fileName = subcategory.getReferenceId() + ".png";
    String pictureUrl = fileService.saveFile(file, SUBCATEGORY_PICTURE_STORAGE_LOCATION, fileName, SUBCATEGORY_PICTURE);
    if (subcategory.getPictureUrl() == null) {
      subcategory.setPictureUrl(pictureUrl);
      subcategoryRepository.save(subcategory);
    }
  }

  @Override
  public void deleteSubcategoryPicture(Long id) {
    SubcategoryEntity subcategory = subcategoryService.findSubcategoryById(id);
    if (subcategory.getPictureUrl() == null) throw new ApiException(BAD_REQUEST, "Subcategory does not have a picture");
    subcategory.setPictureUrl(null);
    subcategoryRepository.save(subcategory);
    fileService.deleteFile(SUBCATEGORY_PICTURE_STORAGE_LOCATION, subcategory.getReferenceId() + ".png");
  }
}
