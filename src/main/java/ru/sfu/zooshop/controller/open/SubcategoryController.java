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
import ru.sfu.zooshop.dto.response.open.subcategory.RichSubcategoryResponse;
import ru.sfu.zooshop.service.SubcategoryService;

import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
@Validated
public class SubcategoryController {
  private final SubcategoryService subcategoryService;

  @GetMapping("/{categorySlug}/subcategory/{subcategorySlug}")
  public ResponseEntity<Response> get(
    HttpServletRequest request,
    @PathVariable("categorySlug") @NotBlank(message = "Category slug must not be empty") String categorySlug,
    @PathVariable("subcategorySlug") @NotBlank(message = "Subcategory slug must not be empty") String subcategorySlug,
    Pageable pageable
  ) {
    RichSubcategoryResponse subcategory = subcategoryService.getSubcategoryBySlug(categorySlug, subcategorySlug, pageable);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Subcategory retrieved",
      subcategory
    ));
  }
}
