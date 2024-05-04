package ru.sfu.zooshop.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.enumeration.JwtType;

import java.util.Optional;

public interface JwtService {
  void setJwtCookies(HttpServletResponse response, String userId, String role);
  void unsetJwtCookies(HttpServletRequest request, HttpServletResponse response);
  Optional<String> getJwtFromRequestCookie(HttpServletRequest request, JwtType type);
  boolean isJwtValid(String jwt);
  UserEntity getUserFromJwt(String jwt);
}
