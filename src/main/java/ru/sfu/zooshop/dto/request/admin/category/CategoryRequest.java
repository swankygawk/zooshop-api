package ru.sfu.zooshop.dto.request.admin.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryRequest {
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
