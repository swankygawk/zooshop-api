package ru.sfu.zooshop.security;

import ru.sfu.zooshop.entity.UserEntity;

public class RequestContext {
  private static final ThreadLocal<UserEntity> USER = new ThreadLocal<>();

  private RequestContext() {}

  public static void reset() {
    USER.remove();
  }

  public static UserEntity getUser() {
    return USER.get();
  }

  public static void setUser(UserEntity userEntity) {
    USER.set(userEntity);
  }
}
