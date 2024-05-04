package ru.sfu.zooshop.dto.request.open.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInRequest {
  @Email(message = "Invalid email")
  @Size(
    max = 255,
    message = "Email must be at most 255 characters long"
  )
  @NotBlank(message = "Email must not be empty")
  private String email;

  @Size(
    min = 8,
    max = 255,
    message = "Password must be 8-255 characters long"
  )
  @NotBlank(message = "Password must not be empty")
  private String password;

  // TODO change 6 to constant
  @Pattern(
    regexp = "^[0-9]{6}$",
    message = "Invalid one-time password format"
  )
  private String otp;

  @Pattern(
    regexp = "^(?:[a-zA-Z0-9]{4}-?){3}[a-zA-Z0-9]{4}$",
    message = "Invalid recovery code format"
  )
  private String recoveryCode;
}
