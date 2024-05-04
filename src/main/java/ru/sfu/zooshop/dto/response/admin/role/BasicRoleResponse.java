package ru.sfu.zooshop.dto.response.admin.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicRoleResponse {
  private Long id;
  private String name;
}
