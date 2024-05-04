package ru.sfu.zooshop.dto.request.open.user;

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
public class EmailRequest {
  @Email(message = "Invalid email")
  @Size(
    max = 255,
    message = "Email must be at most 255 characters long"
  )
  @NotBlank(message = "Email must not be empty")
  private String email;
}
