package ru.sfu.zooshop.dto.response.user.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfu.zooshop.dto.response.user.productpicture.ProductPictureResponse;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortProductResponse {
  private Long id;
  private String name;
  private String slug;
  private List<ProductPictureResponse> pictures;
}
