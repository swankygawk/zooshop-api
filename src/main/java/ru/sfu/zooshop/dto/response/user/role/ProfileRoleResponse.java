package ru.sfu.zooshop.dto.response.user.role;

import lombok.*;
import ru.sfu.zooshop.dto.response.user.authority.ProfileAuthorityResponse;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRoleResponse {
  private String name;
  private Set<ProfileAuthorityResponse> authorities;
}
