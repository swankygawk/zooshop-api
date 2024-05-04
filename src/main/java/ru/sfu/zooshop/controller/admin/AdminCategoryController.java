package ru.sfu.zooshop.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.category.CategoryRequest;
import ru.sfu.zooshop.dto.response.Response;
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

  private URI getCategoryUri(Long id) {
    return URI.create("http://localhost:8080/api/v1/admin/category/" + id.toString());
  }

  // get rich all categories info
  // get rich category info

  @PostMapping
  @PreAuthorize("hasAuthority('CATEGORY:CREATE')")
  public ResponseEntity<Response> createCategory(
    HttpServletRequest request,
    @RequestBody @Valid CategoryRequest categoryRequest
  ) {
    Long id = adminCategoryService.createCategory(categoryRequest);
    return ResponseEntity.created(getCategoryUri(id)).body(getResponse(
      request,
      CREATED,
      "Category created",
      null
    ));
  }

  @PatchMapping("/{id}/update")
  @PreAuthorize("hasAuthority('CATEGORY:UPDATE')")
  public ResponseEntity<Response> updateCategory(
    HttpServletRequest request,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id,
    @RequestBody @Valid CategoryRequest categoryRequest
  ) {
    adminCategoryService.updateCategory(id, categoryRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Category updated",
      null
    ));
  }

  @PutMapping("/{id}/update/picture")
  @PreAuthorize("hasAuthority('CATEGORY:UPDATE')")
  public ResponseEntity<Response> updateCategoryPicture(
    HttpServletRequest request,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id,
    @RequestParam("file") MultipartFile file
  ) {
    adminCategoryService.updateCategoryPicture(id, file);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Category picture updated",
      null
    ));
  }

  @DeleteMapping("/{id}/update/picture")
  @PreAuthorize("hasAuthority('CATEGORY:UPDATE')")
  public ResponseEntity<Response> deleteCategoryPicture(
    HttpServletRequest request,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id
  ) {
    adminCategoryService.deleteCategoryPicture(id);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Category picture deleted",
      null
    ));
  }
}
