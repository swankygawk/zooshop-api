package ru.sfu.zooshop.dto.request.admin.subcategory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubcategoryUpdateRequest {
  @NotNull(message = "Parent ID must not be null")
  @PositiveOrZero(message = "Parent ID must be greater or equal to 0")
  private Long parentId;

  @NotBlank(message = "Name must not be empty")
  @Size(
    max = 255,
    message = "Name must be at most 255 characters long"
  )
  @Pattern(
    regexp = "(?U)^(?:[^\\W\\d_]| )+$",
    message = "Name can only contain Unicode letters and spaces"
  )
  private String name;
}
