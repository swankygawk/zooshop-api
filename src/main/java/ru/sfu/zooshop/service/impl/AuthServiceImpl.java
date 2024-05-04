package ru.sfu.zooshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.cache.ApiCache;
import ru.sfu.zooshop.dto.request.open.user.EmailRequest;
import ru.sfu.zooshop.dto.request.open.user.PasswordResetRequest;
import ru.sfu.zooshop.dto.request.open.user.SignUpRequest;
import ru.sfu.zooshop.entity.CredentialEntity;
import ru.sfu.zooshop.entity.RoleEntity;
import ru.sfu.zooshop.entity.TokenEntity;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.enumeration.SignInType;
import ru.sfu.zooshop.event.UserEvent;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.repository.UserRepository;
import ru.sfu.zooshop.security.RequestContext;
import ru.sfu.zooshop.service.AuthService;
import ru.sfu.zooshop.service.EmailService;
import ru.sfu.zooshop.service.CredentialService;
import ru.sfu.zooshop.service.RoleService;
import ru.sfu.zooshop.service.TokenService;
import ru.sfu.zooshop.service.UserService;

import java.util.Map;

import static java.time.OffsetDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static ru.sfu.zooshop.enumeration.UserEventType.PASSWORD_RESET;
import static ru.sfu.zooshop.enumeration.UserEventType.SIGN_UP;
import static ru.sfu.zooshop.utility.UserUtility.getNewUser;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
  private final UserRepository userRepository;
  private final EmailService emailService;
  private final RoleService roleService;
  private final CredentialService credentialService;
  private final TokenService tokenService;
  private final UserService userService;
  private final ApiCache<String, Integer> userCache;
  private final ApplicationEventPublisher eventPublisher;

  private UserEntity createNewUser(String email, CredentialEntity credential, String firstName, String lastName) {
    RoleEntity role = roleService.findRoleByName("USER");
    return getNewUser(email, credential, firstName, lastName, role);
  }

  @Override
  public void signUp(SignUpRequest request) {
    emailService.validateEmail(request.getEmail());
    CredentialEntity credential = credentialService.createCredential(request.getPassword());
    UserEntity user = userRepository.save(
      createNewUser(request.getEmail(), credential, request.getFirstName(), request.getLastName())
    );
    TokenEntity verificationToken = tokenService.getOrCreate(user);
    eventPublisher.publishEvent(new UserEvent(
      user,
      SIGN_UP,
      Map.of("token", verificationToken.getValue()))
    );
  }

  @Override
  public void resendVerificationToken(EmailRequest request) {
    UserEntity user = userService.findUserByEmail(request.getEmail());
    if (user.isEnabled()) {
      throw new ApiException(BAD_REQUEST, "Account is already verified");
    }
    TokenEntity verificationToken = tokenService.getOrCreate(user);
    eventPublisher.publishEvent(new UserEvent(
      user,
      SIGN_UP,
      Map.of("token", verificationToken.getValue()))
    );
  }

  @Override
  public void verifyByEmail(String token) {
    TokenEntity verificationToken = tokenService.getTokenByValue(token);
    UserEntity user = verificationToken.getUser();
    user.setEnabled(true);
    userRepository.save(user);
    tokenService.deleteToken(verificationToken);
  }

  @Override
  public UserEntity updateSignInAttempt(UserEntity user, SignInType signInType) {
    RequestContext.setUser(user);
    switch (signInType) {
      case SIGN_IN_ATTEMPT -> {
        if (userCache.get(user.getEmail()) == null) {
          user.setSignInAttempts(0);
          user.setTemporarilyLocked(false);
        }
        user.setSignInAttempts(user.getSignInAttempts() + 1);
        userCache.put(user.getEmail(), user.getSignInAttempts());
        if (userCache.get(user.getEmail()) > 5) {
          user.setTemporarilyLocked(true);
        }
      }
      case SIGN_IN_SUCCESS -> {
        user.setSignInAttempts(0);
        user.setTemporarilyLocked(false);
        user.setLastSignIn(now());
        userCache.invalidate(user.getEmail());
      }
      default -> {}
    }
    return userRepository.save(user);
  }

  @Override
  public void requestPasswordReset(EmailRequest request) {
    UserEntity user = userService.findUserByEmail(request.getEmail());
    try {
      userService.validateAccount(user);
    } catch (CredentialsExpiredException _) {} // Allow the user to update their expired credentials
    TokenEntity resetToken = tokenService.getOrCreate(user);
    eventPublisher.publishEvent(new UserEvent(
      user,
      PASSWORD_RESET,
      Map.of("token", resetToken.getValue()))
    );
  }

  @Override
  public void resetPassword(String token, PasswordResetRequest request) {
    TokenEntity resetToken = tokenService.getTokenByValue(token);
    UserEntity user = resetToken.getUser();
    credentialService.updateCredential(user, request.getNewPassword());
    tokenService.deleteToken(resetToken);
  }
}
