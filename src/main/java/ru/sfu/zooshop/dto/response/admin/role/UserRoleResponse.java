package ru.sfu.zooshop.dto.response.admin.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfu.zooshop.dto.response.admin.authority.AuthorityResponse;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleResponse {
  private Long id;
  private String name;
  private Set<AuthorityResponse> authorities;
}
