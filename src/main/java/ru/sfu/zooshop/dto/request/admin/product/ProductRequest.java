package ru.sfu.zooshop.dto.request.admin.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductRequest {
  @NotNull(message = "Category ID must not be null")
  @PositiveOrZero(message = "Category ID must be greater or equal to 0")
  private Long categoryId;

  @NotNull(message = "Subcategory ID must not be null")
  @PositiveOrZero(message = "Subcategory ID must be greater or equal to 0")
  private Long subcategoryId;

  @NotBlank(message = "Name must not be empty")
  @Size(
    max = 255,
    message = "Name must be at most 255 characters long"
  )
  @Pattern(
    regexp = "(?U)^(?:[^\\W_]| )+$",
    message = "Name can only contain Unicode letters, digits and spaces"
  )
  private String name;

  @Size(
    max = 10000,
    message = "Description must be at most 10000 characters long"
  )
  private String description;

  @NotNull(message = "Price must not be null")
  @Digits(
    integer = 10,
    fraction = 2,
    message = "Price must have at most 10 digits in the integral part and at most 2 digits in the fractional part"
  )
  @DecimalMin(
    value = "0.0",
    inclusive = false,
    message = "Price must be greater than 0"
  )
  private BigDecimal price;
}
