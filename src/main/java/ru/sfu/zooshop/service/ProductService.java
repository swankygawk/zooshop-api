package ru.sfu.zooshop.service;

import org.springframework.data.domain.Pageable;
import ru.sfu.zooshop.dto.response.open.product.AllProductsResponse;
import ru.sfu.zooshop.dto.response.open.product.RichProductResponse;
import ru.sfu.zooshop.dto.response.open.review.AllReviewsResponse;
import ru.sfu.zooshop.entity.ProductEntity;

public interface ProductService {
  ProductEntity findProductById(Long id);
  ProductEntity findProductBySlug(String slug);
  AllProductsResponse getProducts(Pageable pageable);
  RichProductResponse getProduct(String slug);
  AllReviewsResponse getProductReviews(String slug, Pageable pageable);
}
