package ru.sfu.zooshop.service;

import ru.sfu.zooshop.entity.CredentialEntity;
import ru.sfu.zooshop.entity.UserEntity;

public interface CredentialService {
  CredentialEntity createCredential(String rawPassword);
  void validateCredential(UserEntity user, String rawPassword);
  void updateCredential(UserEntity user, String rawNewPassword);
}
