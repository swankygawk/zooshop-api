package ru.sfu.zooshop.dto.response.admin.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfu.zooshop.dto.response.admin.productpicture.AdminBasicProductPictureResponse;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminBasicProductResponse {
  private Long id;
  private String name;
  private String slug;
  private List<AdminBasicProductPictureResponse> pictures;
  private BigDecimal price;
  private boolean inStock;
  private Float averageRating;
  private Integer popularity;
  private boolean hidden;
}
