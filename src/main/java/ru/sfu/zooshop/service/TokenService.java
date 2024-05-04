package ru.sfu.zooshop.service;

import ru.sfu.zooshop.entity.TokenEntity;
import ru.sfu.zooshop.entity.UserEntity;

public interface TokenService {
  TokenEntity getOrCreate(UserEntity user);
  TokenEntity getOrCreate(UserEntity user, String additionalData);
  TokenEntity getTokenByValue(String value);
  void deleteToken(TokenEntity token);
}
