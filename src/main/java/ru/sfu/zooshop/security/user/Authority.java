package ru.sfu.zooshop.security.user;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {
  private String resource;
  private String action;

  @Override
  public String getAuthority() {
    return resource + ":" + action;
  }
}
