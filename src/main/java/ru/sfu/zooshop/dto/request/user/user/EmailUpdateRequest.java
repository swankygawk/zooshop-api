package ru.sfu.zooshop.dto.request.user.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class EmailUpdateRequest {
  @Email(message = "Invalid new email")
  @Size(
    max = 255,
    message = "New email must be at most 255 characters long"
  )
  @NotBlank(message = "New email must not be empty")
  private String newEmail;

  @Size(
    min = 8,
    max = 255,
    message = "Password must be 8-255 characters long"
  )
  @NotBlank(message = "Password must not be empty")
  private String password;
}