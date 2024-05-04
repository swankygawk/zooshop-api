package ru.sfu.zooshop.dto.response.admin.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllRolesResponse {
  private List<BasicRoleResponse> roles;
}
