package ru.sfu.zooshop.dto.request.admin.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleRequest {
  @NotBlank(message = "Name must not be empty")
  @Size(
    max = 255,
    message = "Name must be at most 255 characters long"
  )
  private String name;

  private Set<Long> authorities;
}
