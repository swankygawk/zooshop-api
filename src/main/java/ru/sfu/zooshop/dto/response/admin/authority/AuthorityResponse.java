package ru.sfu.zooshop.dto.response.admin.authority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityResponse {
  private Long id;
  private String resource;
  private String action;
}
