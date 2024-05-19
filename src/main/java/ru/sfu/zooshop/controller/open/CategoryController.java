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
import ru.sfu.zooshop.dto.response.open.category.AllCategoriesResponse;
import ru.sfu.zooshop.dto.response.open.category.RichCategoryResponse;
import ru.sfu.zooshop.service.CategoryService;

import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<Response> getAll(HttpServletRequest request) {
    AllCategoriesResponse categories = categoryService.getCategories();
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Categories retrieved",
      categories
    ));
  }

  @GetMapping("/{slug}")
  public ResponseEntity<Response> get(
    HttpServletRequest request,
    @PathVariable("slug") @NotBlank(message = "Slug must not be empty") String slug,
    Pageable pageable
  ) {
    RichCategoryResponse category = categoryService.getCategoryBySlug(slug, pageable);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Category retrieved",
      category
    ));
  }

}
