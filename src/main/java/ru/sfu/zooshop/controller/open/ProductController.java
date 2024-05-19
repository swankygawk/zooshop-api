package ru.sfu.zooshop.controller.open;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.dto.response.open.product.AllProductsResponse;
import ru.sfu.zooshop.dto.response.open.product.RichProductResponse;
import ru.sfu.zooshop.dto.response.open.review.AllReviewsResponse;
import ru.sfu.zooshop.service.ProductService;

import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
@Validated
public class ProductController {
  private final ProductService productService;

  @GetMapping
  public ResponseEntity<Response> getAll(
    HttpServletRequest request,
    Pageable pageable
  ) {
    AllProductsResponse products = productService.getProducts(pageable);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Products retrieved",
      products
    ));
  }

  @GetMapping("/{slug}")
  public ResponseEntity<Response> get(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug
  ) {
    RichProductResponse product = productService.getProduct(slug);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Product retrieved",
      product
    ));
  }

  @GetMapping("/{slug}/review")
  public ResponseEntity<Response> getReviews(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug,
    Pageable pageable
  ) {
    AllReviewsResponse reviews = productService.getProductReviews(slug, pageable);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Product reviews retrieved",
      reviews
    ));
  }
}
