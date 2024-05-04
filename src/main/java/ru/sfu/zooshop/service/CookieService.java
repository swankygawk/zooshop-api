package ru.sfu.zooshop.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Optional;

public interface CookieService {
  Optional<Cookie> getCookie(HttpServletRequest request, String name);
  void setCookie(HttpServletResponse response, String name, String value, Integer maxAge);
  void unsetCookie(HttpServletRequest request, HttpServletResponse response, String name);
}
