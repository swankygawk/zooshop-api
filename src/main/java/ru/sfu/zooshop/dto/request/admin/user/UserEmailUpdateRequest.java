package ru.sfu.zooshop.dto.request.admin.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEmailUpdateRequest {
  @Email(message = "Invalid new email")
  @Size(
    max = 255,
    message = "New email must be at most 255 characters long"
  )
  @NotBlank(message = "New email must not be empty")
  private String newEmail;
}
