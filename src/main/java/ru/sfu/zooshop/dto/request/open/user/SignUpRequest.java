package ru.sfu.zooshop.dto.request.open.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sfu.zooshop.constraint.MatchingValues;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@MatchingValues(
  fieldName = "password",
  matchingFieldName = "passwordConfirmation",
  message = "Password and password confirmation must match"
)
public class SignUpRequest {
  @NotBlank(message = "Email must not be empty")
  @Size(
    max = 255,
    message = "Email must be at most 255 characters long"
  )
  @Email(message = "Invalid email")
  private String email;

  @NotBlank(message = "Password must not be empty")
  @Size(
    min = 8,
    max = 255,
    message = "Password must be 8-255 characters long"
  )
  private String password;

  @NotBlank(message = "Password confirmation must not be empty")
  @Size(
    min = 8,
    max = 255,
    message = "Password confirmation must be 8-255 characters long"
  )
  private String passwordConfirmation;

  @NotBlank(message = "First name must not be empty")
  @Size(
    max = 255,
    message = "First name must be at most 255 characters long"
  )
  private String firstName;

  @NotBlank(message = "Last name must not be empty")
  @Size(
    max = 255,
    message = "Last name must be at most 255 characters long"
  )
  private String lastName;
}
