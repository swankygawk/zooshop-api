package ru.sfu.zooshop.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.sfu.zooshop.constant.Constant.JWT_ACCESS_EXPIRATION_SECONDS;
import static ru.sfu.zooshop.constant.Constant.JWT_REFRESH_EXPIRATION_SECONDS;

@Getter
@AllArgsConstructor
public enum JwtType {
  ACCESS("access-token", JWT_ACCESS_EXPIRATION_SECONDS),
  REFRESH("refresh-token", JWT_REFRESH_EXPIRATION_SECONDS);

  private final String cookieName;
  private final Integer expiration;
}
