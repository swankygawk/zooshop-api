package ru.sfu.zooshop.dto.response.open.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfu.zooshop.dto.response.open.productpicture.ProductPictureResponse;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicProductResponse {
  private Long id;
  private String name;
  private String slug;
  private List<ProductPictureResponse> pictures;
  private BigDecimal price;
  private boolean inStock;
  private Float averageRating;
}
