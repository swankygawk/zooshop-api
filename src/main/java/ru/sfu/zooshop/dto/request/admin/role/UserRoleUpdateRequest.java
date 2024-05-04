package ru.sfu.zooshop.dto.request.admin.role;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRoleUpdateRequest {
  @PositiveOrZero(message = "ID must be greater or equal to 0")
  @NotNull(message = "ID must not be null")
  private Long id;
}
