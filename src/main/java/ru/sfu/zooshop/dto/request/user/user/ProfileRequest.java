package ru.sfu.zooshop.dto.request.user.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
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
public class ProfileRequest {
  @Size(
    max = 255,
    message = "First name must be at most 255 characters long"
  )
  @NotBlank(message = "First name must not be empty")
  private String firstName;

  @Size(
    max = 255,
    message = "Last name must be at most 255 characters long"
  )
  @NotBlank(message = "Last name must not be empty")
  private String lastName;

  @Size(
    max = 255,
    message = "Phone must be at most 255 characters long"
  )
  private String phone;
}
