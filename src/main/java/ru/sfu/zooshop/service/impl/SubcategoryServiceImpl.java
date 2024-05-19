package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.dto.response.open.product.BasicProductResponse;
import ru.sfu.zooshop.dto.response.open.subcategory.RichSubcategoryResponse;
import ru.sfu.zooshop.entity.CategoryEntity;
import ru.sfu.zooshop.entity.SubcategoryEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.OpenMapper;
import ru.sfu.zooshop.repository.ProductRepository;
import ru.sfu.zooshop.repository.SubcategoryRepository;
import ru.sfu.zooshop.service.SubcategoryService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class SubcategoryServiceImpl implements SubcategoryService {
  private final SubcategoryRepository subcategoryRepository;
  private final ProductRepository productRepository;
  private final OpenMapper openMapper;

  private SubcategoryEntity findSubcategoryBySlug(String categorySlug, String subcategorySlug) {
    return subcategoryRepository.findByParentSlugAndSlugAndHiddenFalse(categorySlug, subcategorySlug)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Subcategory with category slug " + categorySlug + " and slug " + subcategorySlug + " not found"));
  }

  private Page<BasicProductResponse> getProductsBySubcategory(SubcategoryEntity subcategory, Pageable pageable) {
    return productRepository.findAllBySubcategoryAndHiddenFalse(subcategory, pageable)
      .map(openMapper::productEntityToBasicProductResponse);
  }

  @Override
  public List<SubcategoryEntity> getSubcategoriesByParent(CategoryEntity parent) {
    return subcategoryRepository.findAllByParentAndHiddenFalse(parent);
  }

  @Override
  public RichSubcategoryResponse getSubcategoryBySlug(String categorySlug, String subcategorySlug, Pageable pageable) {
    SubcategoryEntity subcategoryEntity = findSubcategoryBySlug(categorySlug, subcategorySlug);
    Page<BasicProductResponse> products = getProductsBySubcategory(subcategoryEntity, pageable);
    RichSubcategoryResponse subcategory = openMapper.subcategoryEntityToRichSubcategoryResponse(subcategoryEntity);
    subcategory.setProducts(products);
    return subcategory;
  }
}
