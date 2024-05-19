package ru.sfu.zooshop.dto.response.open.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfu.zooshop.dto.response.open.category.BasicCategoryResponse;
import ru.sfu.zooshop.dto.response.open.productpicture.ProductPictureResponse;
import ru.sfu.zooshop.dto.response.open.subcategory.BasicSubcategoryResponse;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RichProductResponse {
  private Long id;
  private BasicCategoryResponse category;
  private BasicSubcategoryResponse subcategory;
  private String name;
  private String slug;
  private String description;
  private List<ProductPictureResponse> pictures;
  private BigDecimal price;
  private boolean inStock;
  private Float averageRating;
}
