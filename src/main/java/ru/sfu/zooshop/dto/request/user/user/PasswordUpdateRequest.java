package ru.sfu.zooshop.dto.request.user.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
  fieldName = "newPassword",
  matchingFieldName = "newPasswordConfirmation",
  message = "New password and new password confirmation must match"
)
public class PasswordUpdateRequest {
  @NotBlank(message = "Old password must not be empty")
  @Size(
    min = 8,
    max = 255,
    message = "Old password must be 8-255 characters long"
  )
  private String oldPassword;

  @NotBlank(message = "New password must not be empty")
  @Size(
    min = 8,
    max = 255,
    message = "New password must be 8-255 characters long"
  )
  private String newPassword;

  @NotBlank(message = "New password confirmation must not be empty")
  @Size(
    min = 8,
    max = 255,
    message = "New password confirmation must be 8-255 characters long"
  )
  private String newPasswordConfirmation;
}
