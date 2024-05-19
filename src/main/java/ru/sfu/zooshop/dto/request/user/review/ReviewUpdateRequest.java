package ru.sfu.zooshop.dto.request.user.review;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewUpdateRequest {
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
