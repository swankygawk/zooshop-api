package ru.sfu.zooshop.service;

import ru.sfu.zooshop.entity.UserEntity;

import java.util.Set;

public interface MfaService {
  Set<String> createRecoveryCodes(UserEntity user);
  Set<String> resetRecoveryCodes(UserEntity user);
  void deleteRecoveryCodes(UserEntity user);
  void validateMfaCredentials(UserEntity user, String otp, String recoveryCode);
}
