package ru.sfu.zooshop.utility;

import ru.sfu.zooshop.entity.CredentialEntity;
import ru.sfu.zooshop.entity.RoleEntity;
import ru.sfu.zooshop.entity.UserEntity;

import static java.util.UUID.randomUUID;

public final class UserUtility {
  public static UserEntity getNewUser(
    String email,
    CredentialEntity credential,
    String firstName,
    String lastName,
    RoleEntity role
  ) {
    return UserEntity.builder()
      .userId(randomUUID().toString())
      .email(email)
      .credential(credential)
      .firstName(firstName)
      .lastName(lastName)
      .lastSignIn(null)
      .signInAttempts(0)
      .accountNonLocked(true)
      .enabled(false)
      .mfaEnabled(false)
      .role(role)
      .build();
  }
}
