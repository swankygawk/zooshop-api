package ru.sfu.zooshop.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.http.HttpMethod.POST;
import static ru.sfu.zooshop.constant.Constant.SIGN_OUT_PATH;

@RequiredArgsConstructor
@Component
public class ApiSignOutFilter implements Filter {
  private static final RequestMatcher requestMatcher = new AntPathRequestMatcher(SIGN_OUT_PATH, POST.name());
  private final ApiSignOutHandler signOutHandler;
  private final ApiSignOutSuccessHandler signOutSuccessHandler;

  private boolean requiresSignOut(HttpServletRequest request) {
    return requestMatcher.matches(request);
  }

  private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    if (requiresSignOut(request)) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication == null) {
        throw new AuthenticationCredentialsNotFoundException("You are not signed in");
      }
      this.signOutHandler.signOut(request, response, authentication);
      this.signOutSuccessHandler.onSignOutSuccess(request, response);
      return;
    }
    chain.doFilter(request, response);
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    this.doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
  }
}
