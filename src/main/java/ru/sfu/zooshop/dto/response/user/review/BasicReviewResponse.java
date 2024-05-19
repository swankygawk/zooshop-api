package ru.sfu.zooshop.dto.response.user.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfu.zooshop.dto.response.user.product.ShortProductResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicReviewResponse {
  private Long id;
  private ShortProductResponse product;
  private Short rating;
}
