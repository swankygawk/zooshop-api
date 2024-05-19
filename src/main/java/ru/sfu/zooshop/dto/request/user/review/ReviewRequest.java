package ru.sfu.zooshop.dto.request.user.review;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewRequest {
  @NotNull(message = "Product ID must not be null")
  @PositiveOrZero(message = "Product ID must be greater or equal to 0")
  private Long productId;

  @NotNull(message = "Rating must not be null")
  @Min(
    value = 1,
    message = "Rating must be in range of 1 to 5 (inclusive)"
  )
  @Max(
    value = 5,
    message = "Rating must be in range of 1 to 5 (inclusive)"
  )
  private Short rating;

  @Size(
    max = 255,
    message = "Comment must be at most 255 characters long"
  )
  private String comment;
}
