package ru.sfu.zooshop.security.user;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {
  private String name;
  private Set<Authority> authorities;
}
