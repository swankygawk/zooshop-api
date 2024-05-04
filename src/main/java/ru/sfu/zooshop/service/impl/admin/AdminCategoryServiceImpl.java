package ru.sfu.zooshop.service.impl.admin;

import com.github.slugify.Slugify;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.category.CategoryRequest;
import ru.sfu.zooshop.entity.CategoryEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.category.CategoryMapper;
import ru.sfu.zooshop.repository.CategoryRepository;
import ru.sfu.zooshop.service.CategoryService;
import ru.sfu.zooshop.service.admin.AdminCategoryService;
import ru.sfu.zooshop.service.FileService;

import java.util.Objects;

import static org.apache.commons.text.WordUtils.capitalizeFully;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static ru.sfu.zooshop.constant.Constant.CATEGORY_PICTURE_STORAGE_LOCATION;
import static ru.sfu.zooshop.enumeration.FileType.CATEGORY_PICTURE;
import static ru.sfu.zooshop.utility.SystemUtility.convert;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class AdminCategoryServiceImpl implements AdminCategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryService categoryService;
  private final FileService fileService;
  private final CategoryMapper categoryMapper;
  private final Slugify slugifier;

  private void validateName(String name) {
    if (categoryRepository.existsByName(name)) throw new ApiException(CONFLICT, "Category with name " + name + " already exists");
  }

  @Override
  public Long createCategory(CategoryRequest request) {
    String name = convert(request.getName());
    validateName(name);
    CategoryEntity category = categoryRepository.save(
      new CategoryEntity(
        name,
        slugifier.slugify(name)
      )
    );
    return category.getId();
  }

  @Override
  public void updateCategory(Long id, CategoryRequest request) {
    CategoryEntity category = categoryService.findCategoryById(id);
    String name = convert(request.getName());
    if (!Objects.equals(category.getName(), name)) {
      validateName(request.getName());
    }
    category.setName(name);
    category.setSlug(slugifier.slugify(name));
    categoryRepository.save(category);
  }

  @Override
  public void updateCategoryPicture(Long id, MultipartFile file) {
    CategoryEntity category = categoryService.findCategoryById(id);
    String fileName = category.getReferenceId() + ".png";
    String pictureUrl = fileService.saveFile(file, CATEGORY_PICTURE_STORAGE_LOCATION, fileName, CATEGORY_PICTURE);
    if (category.getPictureUrl() == null) {
      category.setPictureUrl(pictureUrl);
      categoryRepository.save(category);
    }
  }

  @Override
  public void deleteCategoryPicture(Long id) {
    CategoryEntity category = categoryService.findCategoryById(id);
    if (category.getPictureUrl() == null) throw new ApiException(BAD_REQUEST, "Category does not have a picture");
    category.setPictureUrl(null);
    categoryRepository.save(category);
    fileService.deleteFile(CATEGORY_PICTURE_STORAGE_LOCATION, category.getReferenceId() + ".png");
  }
}
