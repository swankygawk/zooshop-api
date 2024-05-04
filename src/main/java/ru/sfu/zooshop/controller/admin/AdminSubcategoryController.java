package ru.sfu.zooshop.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.admin.subcategory.SubcategoryRequest;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.service.admin.AdminSubcategoryService;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/subcategory")
@Validated
public class AdminSubcategoryController {
  private final AdminSubcategoryService adminSubcategoryService;

  private URI getSubcategoryUri(Long id) {
    return URI.create("http://localhost:8080/api/v1/admini/subcategory/" + id.toString());
  }

  @PostMapping
  @PreAuthorize("hasAuthority('SUBCATEGORY:CREATE')")
  public ResponseEntity<Response> createSubcategory(
    HttpServletRequest request,
    @RequestBody @Valid SubcategoryRequest subcategoryRequest
  ) {
    Long id = adminSubcategoryService.createSubcategory(subcategoryRequest);
    return ResponseEntity.created(getSubcategoryUri(id)).body(getResponse(
      request,
      CREATED,
      "Subcategory created",
      null
    ));
  }

  @PatchMapping("/{id}/update")
  @PreAuthorize("hasAuthority('SUBCATEGORY:UPDATE')")
  public ResponseEntity<Response> updateSubcategory(
    HttpServletRequest request,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id,
    @RequestBody @Valid SubcategoryRequest subcategoryRequest
  ) {
    adminSubcategoryService.updateSubcategory(id, subcategoryRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Subcategory updated",
      null
    ));
  }

  @PutMapping("/{id}/update/picture")
  @PreAuthorize("hasAuthority('SUBCATEGORY:UPDATE')")
  public ResponseEntity<Response> updateSubcategoryPicture(
    HttpServletRequest request,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id,
    @RequestParam("file") MultipartFile file
  ) {
    adminSubcategoryService.updateSubcategoryPicture(id, file);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Subcategory picture updated",
      null
    ));
  }

  @DeleteMapping("/{id}/update/picture")
  @PreAuthorize("hasAuthority('SUBCATEGORY:UPDATE')")
  public ResponseEntity<Response> deleteSubcategoryPicture(
    HttpServletRequest request,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id
  ) {
    adminSubcategoryService.deleteSubcategoryPicture(id);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Subcategory picture deleted",
      null
    ));
  }
}
