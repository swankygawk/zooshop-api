package ru.sfu.zooshop.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import ru.sfu.zooshop.service.JwtService;

@RequiredArgsConstructor
@Component
public class ApiSignOutHandler {
  private final JwtService jwtService;

  public void signOut(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
    logoutHandler.logout(request, response, authentication);
    jwtService.unsetJwtCookies(request, response);
  }
}
