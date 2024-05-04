package ru.sfu.zooshop.security.user;

import lombok.*;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticatedUser implements AuthenticatedPrincipal, UserDetails {
  private Long id;
  private String referenceId;
  private String userId;
  private Role role;

  @Override
  public String getUsername() {
    return "[EMAIL PROTECTED]";
  }

  @Override
  public String getName() {
    return "[EMAIL PROTECTED]";
  }

  @Override
  public String getPassword() {
    return "[PASSWORD PROTECTED]";
  }

  @Override
  public Collection<Authority> getAuthorities() {
    return this.getRole().getAuthorities();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
}
