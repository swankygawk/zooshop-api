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
import ru.sfu.zooshop.dto.request.admin.subcategory.SubcategoryRequest;
import ru.sfu.zooshop.dto.request.admin.subcategory.SubcategoryUpdateRequest;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.dto.response.admin.subcategory.AdminRichSubcategoryResponse;
import ru.sfu.zooshop.service.admin.AdminSubcategoryService;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/category")
@Validated
public class AdminSubcategoryController {
  private final AdminSubcategoryService adminSubcategoryService;

  private URI getSubcategoryUri(String categorySlug, String subcategorySlug) {
    return URI.create("http://localhost:8080/api/v1/admin/category/" + categorySlug + "/subcategory/" + subcategorySlug);
  }

  @GetMapping("/{categorySlug}/subcategory/{subcategorySlug}")
  @PreAuthorize("hasAuthority('SUBCATEGORY:READ')")
  public ResponseEntity<Response> get(
    HttpServletRequest request,
    @PathVariable("categorySlug") @NotBlank(message = "Category slug must not be empty") String categorySlug,
    @PathVariable("subcategorySlug") @NotBlank(message = "Subcategory slug must not be empty") String subcategorySlug,
    Pageable pageable
  ) {
    AdminRichSubcategoryResponse subcategory = adminSubcategoryService.getSubcategory(categorySlug, subcategorySlug, pageable);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Subcategory retrieved",
      subcategory
    ));
  }

  @PostMapping("/{categorySlug}/subcategory")
  @PreAuthorize("hasAuthority('SUBCATEGORY:CREATE')")
  public ResponseEntity<Response> create(
    HttpServletRequest request,
    @PathVariable("categorySlug") @NotBlank(message = "Category slug must not be empty") String categorySlug,
    @RequestBody @Valid SubcategoryRequest subcategoryRequest
  ) {
    String subcategorySlug = adminSubcategoryService.createSubcategory(categorySlug, subcategoryRequest);
    return ResponseEntity.created(getSubcategoryUri(categorySlug, subcategorySlug)).body(getResponse(
      request,
      CREATED,
      "Subcategory created",
      null
    ));
  }

  @PatchMapping("/{categorySlug}/subcategory/{subcategorySlug}/update")
  @PreAuthorize("hasAuthority('SUBCATEGORY:UPDATE')")
  public ResponseEntity<Response> update(
    HttpServletRequest request,
    @PathVariable("categorySlug") @NotBlank(message = "Category slug must not be empty") String categorySlug,
    @PathVariable("subcategorySlug") @NotBlank(message = "Subcategory slug must not be empty") String subcategorySlug,
    @RequestBody @Valid SubcategoryUpdateRequest subcategoryUpdateRequest
  ) {
    adminSubcategoryService.updateSubcategory(categorySlug, subcategorySlug, subcategoryUpdateRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Subcategory updated",
      null
    ));
  }

  @PutMapping("/{categorySlug}/subcategory/{subcategorySlug}/update/picture")
  @PreAuthorize("hasAuthority('SUBCATEGORY:UPDATE')")
  public ResponseEntity<Response> updatePicture(
    HttpServletRequest request,
    @PathVariable("categorySlug") @NotBlank(message = "Category slug must not be empty") String categorySlug,
    @PathVariable("subcategorySlug") @NotBlank(message = "Subcategory slug must not be empty") String subcategorySlug,
    @RequestParam("file") MultipartFile file
  ) {
    adminSubcategoryService.updateSubcategoryPicture(categorySlug, subcategorySlug, file);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Subcategory productpicture updated",
      null
    ));
  }

  @DeleteMapping("/{categorySlug}/subcategory/{subcategorySlug}/update/picture")
  @PreAuthorize("hasAuthority('SUBCATEGORY:UPDATE')")
  public ResponseEntity<Response> deletePicture(
    HttpServletRequest request,
    @PathVariable("categorySlug") @NotBlank(message = "Category slug must not be empty") String categorySlug,
    @PathVariable("subcategorySlug") @NotBlank(message = "Subcategory slug must not be empty") String subcategorySlug
  ) {
    adminSubcategoryService.deleteSubcategoryPicture(categorySlug, subcategorySlug);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Subcategory productpicture deleted",
      null
    ));
  }

  @PatchMapping("/{categorySlug}/subcategory/{subcategorySlug}/update/hide")
  @PreAuthorize("hasAuthority('SUBCATEGORY:UPDATE')")
  public ResponseEntity<Response> hide(
    HttpServletRequest request,
    @PathVariable("categorySlug") @NotBlank(message = "Category slug must not be empty") String categorySlug,
    @PathVariable("subcategorySlug") @NotBlank(message = "Subcategory slug must not be empty") String subcategorySlug
  ) {
    adminSubcategoryService.toggleSubcategoryVisibility(categorySlug, subcategorySlug, true);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Subcategory hidden",
      null
    ));
  }

  @PatchMapping("/{categorySlug}/subcategory/{subcategorySlug}/update/show")
  @PreAuthorize("hasAuthority('SUBCATEGORY:UPDATE')")
  public ResponseEntity<Response> show(
    HttpServletRequest request,
    @PathVariable("categorySlug") @NotBlank(message = "Category slug must not be empty") String categorySlug,
    @PathVariable("subcategorySlug") @NotBlank(message = "Subcategory slug must not be empty") String subcategorySlug
  ) {
    adminSubcategoryService.toggleSubcategoryVisibility(categorySlug, subcategorySlug, false);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Subcategory shown",
      null
    ));
  }
}
