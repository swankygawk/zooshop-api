package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.dto.response.open.product.AllProductsResponse;
import ru.sfu.zooshop.dto.response.open.product.BasicProductResponse;
import ru.sfu.zooshop.dto.response.open.product.RichProductResponse;
import ru.sfu.zooshop.dto.response.open.review.AllReviewsResponse;
import ru.sfu.zooshop.dto.response.open.review.BasicReviewResponse;
import ru.sfu.zooshop.entity.ProductEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.OpenMapper;
import ru.sfu.zooshop.repository.ProductRepository;
import ru.sfu.zooshop.repository.ReviewRepository;
import ru.sfu.zooshop.service.ProductService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;
  private final ReviewRepository reviewRepository;
  private final OpenMapper openMapper;

  @Override
  public ProductEntity findProductById(Long id) {
    return productRepository.findBySubcategoryHiddenFalseAndIdAndHiddenFalse(id)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Product with ID " + id + " not found"));
  }

  @Override
  public ProductEntity findProductBySlug(String slug) {
    return productRepository.findBySubcategoryHiddenFalseAndSlugAndHiddenFalse(slug)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Product with slug " + slug + " not found"));
  }

  @Override
  public AllProductsResponse getProducts(Pageable pageable) {
    Page<BasicProductResponse> products = productRepository.findAllByHiddenFalse(pageable)
      .map(openMapper::productEntityToBasicProductResponse);
    return new AllProductsResponse(products);
  }

  @Override
  public RichProductResponse getProduct(String slug) {
    ProductEntity productEntity = findProductBySlug(slug);
    return openMapper.productEntityToRichProductResponse(productEntity);
  }

  @Override
  public AllReviewsResponse getProductReviews(String slug, Pageable pageable) {
    ProductEntity product = findProductBySlug(slug);
    Page<BasicReviewResponse> reviews = reviewRepository.findAllByProductAndCommentNotNull(product, pageable)
      .map(openMapper::reviewEntityToBasicReviewResponse);
    return new AllReviewsResponse(reviews);
  }
}
