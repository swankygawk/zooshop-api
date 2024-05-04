package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.entity.SubcategoryEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.repository.SubcategoryRepository;
import ru.sfu.zooshop.service.SubcategoryService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class SubcategoryServiceImpl implements SubcategoryService {
  private final SubcategoryRepository subcategoryRepository;

  @Override
  public SubcategoryEntity findSubcategoryById(Long id) {
    return subcategoryRepository.findById(id)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Subcategory with id " + id + " not found"));
  }

  @Override
  public SubcategoryEntity findSubcategoryBySlug(String slug) {
    return subcategoryRepository.findBySlug(slug)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Category with slug " + slug + " not found"));
  }

  @Override
  public List<SubcategoryEntity> getSubcategoriesByParentId(Long parentId) {
    return subcategoryRepository.findAllByParentId(parentId);
  }

  @Override
  public RichSubcategoryResponse getSubcategoryById(Long id) {
    return null;
  }

  @Override
  public RichSubcategoryResponse getCategoryBySlug(String slug) {
    return null;
  }
}
