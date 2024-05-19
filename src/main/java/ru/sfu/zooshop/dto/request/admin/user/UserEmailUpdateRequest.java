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
  @NotBlank(message = "Email must not be empty")
  @Size(
    max = 255,
    message = "Email must be at most 255 characters long"
  )
  @Email(message = "Invalid email")
  private String email;
}
