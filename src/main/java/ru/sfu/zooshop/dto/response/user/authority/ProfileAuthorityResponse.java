package ru.sfu.zooshop.dto.response.user.authority;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileAuthorityResponse {
  private String resource;
  private String action;
}
