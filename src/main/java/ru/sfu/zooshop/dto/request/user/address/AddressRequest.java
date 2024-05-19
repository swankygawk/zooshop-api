package ru.sfu.zooshop.dto.request.user.address;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressRequest {
  @NotBlank(message = "Last name must not be empty")
  @Size(
    max = 255,
    message = "City must be at most 255 characters long"
  )
  private String city;

  @NotBlank(message = "Street must not be empty")
  @Size(
    max = 255,
    message = "Street must be at most 255 characters long"
  )
  private String street;

  @NotBlank(message = "House must not be empty")
  @Size(
    max = 255,
    message = "House must be at most 255 characters long"
  )
  private String house;

  @Positive(message = "Flat must be a positive number or null")
  private Integer flat;
}
