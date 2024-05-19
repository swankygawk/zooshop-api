package ru.sfu.zooshop.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.category.CategoryRequest;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.dto.response.admin.category.AdminAllCategoriesResponse;
import ru.sfu.zooshop.dto.response.admin.category.AdminRichCategoryResponse;
import ru.sfu.zooshop.service.admin.AdminCategoryService;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/category")
@Validated
public class AdminCategoryController {
  private final AdminCategoryService adminCategoryService;

  private URI getCategoryUri(String slug) {
    return URI.create("http://localhost:8080/api/v1/admin/category/" + slug);
  }

  @GetMapping
  @PreAuthorize("hasAuthority('CATEGORY:READ')")
  public ResponseEntity<Response> getAll(
    HttpServletRequest request
  ) {
    AdminAllCategoriesResponse categories = adminCategoryService.getCategories();
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Categories retrieved",
      categories
    ));
  }

  @GetMapping("/{slug}")
  @PreAuthorize("hasAuthority('CATEGORY:READ')")
  public ResponseEntity<Response> get(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug,
    Pageable pageable
  ) {
    AdminRichCategoryResponse category = adminCategoryService.getCategory(slug, pageable);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Category retrieved",
      category
    ));
  }

  @PostMapping
  @PreAuthorize("hasAuthority('CATEGORY:CREATE')")
  public ResponseEntity<Response> create(
    HttpServletRequest request,
    @RequestBody @Valid CategoryRequest categoryRequest
  ) {
    String slug = adminCategoryService.createCategory(categoryRequest);
    return ResponseEntity.created(getCategoryUri(slug)).body(getResponse(
      request,
      CREATED,
      "Category created",
      null
    ));
  }

  @PatchMapping("/{slug}/update")
  @PreAuthorize("hasAuthority('CATEGORY:UPDATE')")
  public ResponseEntity<Response> update(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug,
    @RequestBody @Valid CategoryRequest categoryRequest
  ) {
    adminCategoryService.updateCategory(slug, categoryRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Category updated",
      null
    ));
  }

  @PutMapping("/{slug}/update/picture")
  @PreAuthorize("hasAuthority('CATEGORY:UPDATE')")
  public ResponseEntity<Response> updatePicture(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug,
    @RequestParam("file") MultipartFile file
  ) {
    adminCategoryService.updateCategoryPicture(slug, file);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Category productpicture updated",
      null
    ));
  }

  @DeleteMapping("/{slug}/update/picture")
  @PreAuthorize("hasAuthority('CATEGORY:UPDATE')")
  public ResponseEntity<Response> deletePicture(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug
  ) {
    adminCategoryService.deleteCategoryPicture(slug);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Category productpicture deleted",
      null
    ));
  }

  @PatchMapping("/{slug}/update/hide")
  @PreAuthorize("hasAuthority('CATEGORY:UPDATE')")
  public ResponseEntity<Response> hide(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug
  ) {
    adminCategoryService.toggleCategoryVisibility(slug, true);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Category hidden",
      null
    ));
  }

  @PatchMapping("/{slug}/update/show")
  @PreAuthorize("hasAuthority('CATEGORY:UPDATE')")
  public ResponseEntity<Response> show(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug
  ) {
    adminCategoryService.toggleCategoryVisibility(slug, false);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Category shown",
      null
    ));
  }
}
