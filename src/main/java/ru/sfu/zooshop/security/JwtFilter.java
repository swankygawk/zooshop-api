package ru.sfu.zooshop.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.mapper.system.SystemMapper;
import ru.sfu.zooshop.security.user.AuthenticatedUser;
import ru.sfu.zooshop.service.JwtService;
import ru.sfu.zooshop.service.UserService;
import ru.sfu.zooshop.utility.ResponseWriter;

import java.util.Optional;

import static org.springframework.http.HttpMethod.OPTIONS;
import static ru.sfu.zooshop.constant.Constant.PUBLIC_ROUTES;
import static ru.sfu.zooshop.enumeration.JwtType.ACCESS;
import static ru.sfu.zooshop.enumeration.JwtType.REFRESH;
import static ru.sfu.zooshop.security.ApiAuthenticationToken.authenticated;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
  private final UserService userService;
  private final JwtService jwtService;
  private final ResponseWriter responseWriter;
  private final SystemMapper systemMapper;

  private ApiAuthenticationToken getAuthentication(HttpServletRequest request, String jwt) {
    UserEntity user = jwtService.getUserFromJwt(jwt);
    AuthenticatedUser authenticatedUser = systemMapper.userEntityToAuthenticatedUser(user);
    ApiAuthenticationToken authenticatedToken = authenticated(authenticatedUser, authenticatedUser.getAuthorities());
    authenticatedToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    return authenticatedToken;
  }

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) {
    // TODO some refactoring to make it all look nicer?
    try {
      SecurityContext securityContext = SecurityContextHolder.getContext();
      Optional<String> accessJwt = jwtService.getJwtFromRequestCookie(request, ACCESS);
      if (accessJwt.isPresent() && jwtService.isJwtValid(accessJwt.get())) {
        securityContext.setAuthentication(getAuthentication(request, accessJwt.get()));
        RequestContext.setUser(jwtService.getUserFromJwt(accessJwt.get()));
      } else {
        Optional<String> refreshJwt = jwtService.getJwtFromRequestCookie(request, REFRESH);
        if (refreshJwt.isPresent() && jwtService.isJwtValid(refreshJwt.get())) {
          securityContext.setAuthentication(getAuthentication(request, refreshJwt.get()));
          UserEntity user = jwtService.getUserFromJwt(refreshJwt.get());
          RequestContext.setUser(user);
          jwtService.setJwtCookies(
            response,
            user.getUserId(),
            user.getRole().getName()
          );
        } else {
          SecurityContextHolder.clearContext();
        }
      }
      filterChain.doFilter(request, response);
    } catch (Exception exception) {
      log.error("doFilterInternal: {}", exception.getMessage());
      responseWriter.writeExceptionResponse(request, response, exception);
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    boolean shouldNotFilter = request.getMethod().equalsIgnoreCase(OPTIONS.name()) || PUBLIC_ROUTES.contains(request.getRequestURI());
    if (shouldNotFilter) {
      RequestContext.setUser(userService.getSystem());
    }
    return shouldNotFilter;
  }
}
