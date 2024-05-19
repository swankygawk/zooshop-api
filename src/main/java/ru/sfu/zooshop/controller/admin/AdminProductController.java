package ru.sfu.zooshop.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.product.ProductRequest;
import ru.sfu.zooshop.dto.request.admin.product.SupplyRequest;
import ru.sfu.zooshop.dto.request.admin.productpicture.ProductPictureOrderRequest;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.dto.response.admin.product.AdminAllProductsResponse;
import ru.sfu.zooshop.dto.response.admin.product.AdminRichProductResponse;
import ru.sfu.zooshop.service.admin.AdminProductService;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/product")
@Validated
public class AdminProductController {
  private final AdminProductService adminProductService;

  private URI getUri(String slug) {
    return URI.create("http://localhost:8080/api/v1/admin/product/" + slug);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('PRODUCT:READ')")
  public ResponseEntity<Response> getAll(
    HttpServletRequest request,
    Pageable pageable
  ) {
    AdminAllProductsResponse products = adminProductService.getProducts(pageable);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Products retrieved",
      products
    ));
  }

  @GetMapping("/{slug}")
  @PreAuthorize("hasAuthority('PRODUCT:READ')")
  public ResponseEntity<Response> get(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug
  ) {
    AdminRichProductResponse product = adminProductService.getProduct(slug);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Product retrieved",
      product
    ));
  }

  // product reviews

  @PostMapping
  @PreAuthorize("hasAuthority('PRODUCT:CREATE')")
  public ResponseEntity<Response> create(
    HttpServletRequest request,
    @RequestBody @Valid ProductRequest productRequest
  ) {
    String slug = adminProductService.createProduct(productRequest);
    return ResponseEntity.created(getUri(slug)).body(getResponse(
      request,
      CREATED,
      "Product created",
      null
    ));
  }

  @PatchMapping("/{slug}/update")
  @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
  public ResponseEntity<Response> update(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug,
    @RequestBody @Valid ProductRequest productRequest
  ) {
    adminProductService.updateProduct(slug, productRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Product updated",
      null
    ));
  }

  @PatchMapping("/{slug}/update/hide")
  @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
  public ResponseEntity<Response> hide(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug
  ) {
    adminProductService.toggleProductVisibility(slug, true);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Product hidden",
      null
    ));
  }

  @PatchMapping("/{slug}/update/show")
  @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
  public ResponseEntity<Response> show(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug
  ) {
    adminProductService.toggleProductVisibility(slug, false);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Product shown",
      null
    ));
  }

  @PutMapping("/{slug}/update/picture")
  @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
  public ResponseEntity<Response> uploadPictures(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug,
    @RequestParam("files") @NotEmpty(message = "Files must not be empty") List<MultipartFile> files
  ) {
    adminProductService.uploadProductPictures(slug, files);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Product picture" + (files.size() > 1 ? "s" : "") + " uploaded",
      null
    ));
  }

  @DeleteMapping("/{slug}/update/picture/{id}")
  @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
  public ResponseEntity<Response> deletePicture(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug,
    @PathVariable("id") @NotNull(message = "Picture ID must not be null") @PositiveOrZero(message = "Picture ID must be greater or equal to 0") Long id
  ) {
    adminProductService.deleteProductPicture(slug, id);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Product picture deleted",
      null
    ));
  }

  @PatchMapping("/{slug}/update/picture/order")
  @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
  public ResponseEntity<Response> updatePictureOrder(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug,
    @RequestBody @Valid ProductPictureOrderRequest orderRequest
  ) {
    adminProductService.updatePictureOrder(slug, orderRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Product picture order updated",
      null
    ));
  }

  @PostMapping("/{slug}/supply/add")
  @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
  public ResponseEntity<Response> addSupply(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug,
    @RequestBody @Valid SupplyRequest supplyRequest
  ) {
    adminProductService.addProductSupply(slug, supplyRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Supply added",
      null
    ));
  }

  @PostMapping("/{slug}/supply/remove")
  @PreAuthorize("hasAuthority('PRODUCT:UPDATE')")
  public ResponseEntity<Response> removeSupply(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug,
    @RequestBody @Valid SupplyRequest supplyRequest
  ) {
    adminProductService.removeProductSupply(slug, supplyRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Supply removed",
      null
    ));
  }
}
