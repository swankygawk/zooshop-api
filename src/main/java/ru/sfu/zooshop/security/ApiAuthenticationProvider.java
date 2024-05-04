package ru.sfu.zooshop.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.mapper.system.SystemMapper;
import ru.sfu.zooshop.security.user.AuthenticatedUser;
import ru.sfu.zooshop.service.AuthService;
import ru.sfu.zooshop.service.CredentialService;
import ru.sfu.zooshop.service.MfaService;
import ru.sfu.zooshop.service.UserService;

import static ru.sfu.zooshop.enumeration.SignInType.SIGN_IN_ATTEMPT;
import static ru.sfu.zooshop.enumeration.SignInType.SIGN_IN_SUCCESS;
import static ru.sfu.zooshop.security.ApiAuthenticationToken.authenticated;

@RequiredArgsConstructor
@Component
public class ApiAuthenticationProvider implements AuthenticationProvider {
  private final UserService userService;
  private final AuthService authService;
  private final CredentialService credentialService;
  private final MfaService mfaService;
  private final SystemMapper systemMapper;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    ApiAuthenticationToken unauthenticatedToken = (ApiAuthenticationToken) authentication;
    UserEntity user = userService.findUserByEmail(unauthenticatedToken.getEmail());
    user = authService.updateSignInAttempt(user, SIGN_IN_ATTEMPT);
    userService.validateAccount(user);
    credentialService.validateCredential(user, unauthenticatedToken.getPassword());
    mfaService.validateMfaCredentials(user, unauthenticatedToken.getOtp(), unauthenticatedToken.getRecoveryCode());
    user = authService.updateSignInAttempt(user, SIGN_IN_SUCCESS);
    AuthenticatedUser authenticatedUser = systemMapper.userEntityToAuthenticatedUser(user);
    return authenticated(authenticatedUser, authenticatedUser.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return ApiAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
