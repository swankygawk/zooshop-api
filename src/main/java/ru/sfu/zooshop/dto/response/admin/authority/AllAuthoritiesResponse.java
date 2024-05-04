package ru.sfu.zooshop.dto.response.admin.authority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllAuthoritiesResponse {
  private Set<AuthorityResponse> authorities;
}
