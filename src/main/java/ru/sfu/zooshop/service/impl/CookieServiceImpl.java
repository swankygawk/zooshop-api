package ru.sfu.zooshop.service.impl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.service.CookieService;

import java.util.Objects;
import java.util.Optional;

import static java.util.Arrays.stream;
import static java.util.Optional.empty;
import static org.springframework.boot.web.server.Cookie.SameSite.NONE;

@RequiredArgsConstructor
@Service
public class CookieServiceImpl implements CookieService {

  @Override
  public Optional<Cookie> getCookie(HttpServletRequest request, String name) {
    return request.getCookies() == null
      ? empty()
      : stream(request.getCookies())
      .filter(cookie -> Objects.equals(cookie.getName(), name))
      .findAny();
  }

  @Override
  public void setCookie(HttpServletResponse response, String name, String value, Integer maxAge) {
    Cookie cookie = new Cookie(name, value);
    cookie.setHttpOnly(true);
//    cookie.setSecure(true); // HTTPS required
    cookie.setPath("/");
    cookie.setAttribute("SameSite", NONE.name());
    cookie.setMaxAge(maxAge);
    response.addCookie(cookie);
  }

  @Override
  public void unsetCookie(HttpServletRequest request, HttpServletResponse response, String name) {
    Optional<Cookie> cookie = getCookie(request, name);
    if (cookie.isPresent()) {
      cookie.get().setMaxAge(0);
      response.addCookie(cookie.get());
    }
    // If a cookie with given name is not present it may be considered already unset
  }
}
