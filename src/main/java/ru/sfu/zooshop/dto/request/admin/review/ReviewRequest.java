package ru.sfu.zooshop.dto.request.admin.review;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {
  @Size(
    max = 255,
    message = "Comment must be at most 255 characters long"
  )
  private String comment;
}
