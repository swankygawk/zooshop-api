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
  @NotNull(message = "Role ID must not be null")
  @PositiveOrZero(message = "Role ID must be greater or equal to 0")
  private Long roleId;
}
