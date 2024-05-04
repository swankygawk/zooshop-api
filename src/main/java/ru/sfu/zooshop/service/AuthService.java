package ru.sfu.zooshop.service;

import ru.sfu.zooshop.dto.request.open.user.EmailRequest;
import ru.sfu.zooshop.dto.request.open.user.PasswordResetRequest;
import ru.sfu.zooshop.dto.request.open.user.SignUpRequest;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.enumeration.SignInType;

public interface AuthService {
  void signUp(SignUpRequest signUpRequest);
  void resendVerificationToken(EmailRequest emailRequest);
  void verifyByEmail(String token);
  UserEntity updateSignInAttempt(UserEntity user, SignInType signInType);
  void requestPasswordReset(EmailRequest emailRequest);
  void resetPassword(String token, PasswordResetRequest passwordResetRequest);
}
