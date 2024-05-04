package ru.sfu.zooshop.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.security.user.AuthenticatedUser;

import java.util.Collection;

import static org.springframework.security.core.authority.AuthorityUtils.NO_AUTHORITIES;

@Getter
public class ApiAuthenticationToken extends AbstractAuthenticationToken {
  private static final String EMAIL_PROTECTED = "[EMAIL_PROTECTED]";
  private static final String PASSWORD_PROTECTED = "[PASSWORD_PROTECTED]";

  private final AuthenticatedUser user;
  private final String email;
  private final String password;
  private final String otp;
  private final String recoveryCode;

  private ApiAuthenticationToken(String email, String password, String otp, String recoveryCode) {
    super(NO_AUTHORITIES);
    this.user = null;
    this.email = email;
    this.password = password;
    this.otp = otp;
    this.recoveryCode = recoveryCode;
    super.setAuthenticated(false);
  }

  private ApiAuthenticationToken(AuthenticatedUser user, Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.user = user;
    this.email = EMAIL_PROTECTED;
    this.password = PASSWORD_PROTECTED;
    this.otp = null;
    this.recoveryCode = null;
    super.setAuthenticated(true);
  }

  public static ApiAuthenticationToken unauthenticated(String email, String password, String otp, String recoveryCode) {
    return new ApiAuthenticationToken(email, password, otp, recoveryCode);
  }

  public static ApiAuthenticationToken authenticated(AuthenticatedUser user, Collection<? extends GrantedAuthority> authorities) {
    return new ApiAuthenticationToken(user, authorities);
  }

  @Override
  public AuthenticatedUser getPrincipal() {
    return this.getUser();
  }

  @Override
  public String getCredentials() {
    return PASSWORD_PROTECTED;
  }


  @Override
  public void setAuthenticated(boolean authenticated) {
    throw new ApiException("Modification of authentication token is not allowed");
  }
}
