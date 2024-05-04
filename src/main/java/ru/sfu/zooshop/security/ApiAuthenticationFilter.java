package ru.sfu.zooshop.security;

import com.fasterxml.jackson.databind.ObjectReader;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.sfu.zooshop.dto.request.open.user.SignInRequest;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.service.JwtService;
import ru.sfu.zooshop.utility.ResponseWriter;

import java.util.Set;

import static com.fasterxml.jackson.core.JsonParser.Feature.AUTO_CLOSE_SOURCE;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.constant.Constant.SIGN_IN_PATH;
import static ru.sfu.zooshop.security.ApiAuthenticationToken.unauthenticated;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@Slf4j
public class ApiAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
  private final JwtService jwtService;
  private final ObjectReader objectReader;
  private final ResponseWriter responseWriter;
  private final Validator validator;

  private void validateSignInRequest(SignInRequest request) {
    Set<ConstraintViolation<SignInRequest>> violations = validator.validate(request);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  public ApiAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService, ObjectReader objectReader, ResponseWriter responseWriter, Validator validator) {
    super(new AntPathRequestMatcher(SIGN_IN_PATH, POST.name()), authenticationManager);
    this.jwtService = jwtService;
    this.objectReader = objectReader;
    this.responseWriter = responseWriter;
    this.validator = validator;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      SignInRequest user = this.objectReader.with(AUTO_CLOSE_SOURCE)
        .readValue(request.getInputStream(), SignInRequest.class);
      this.validateSignInRequest(user);

      ApiAuthenticationToken unauthenticatedToken = unauthenticated(
        user.getEmail(),
        user.getPassword(),
        user.getOtp(),
        user.getRecoveryCode()
      );
      return getAuthenticationManager().authenticate(unauthenticatedToken);
    } catch (Exception exception) {
      log.error("attemptAuthentication: {}", exception.getMessage());
      responseWriter.writeExceptionResponse(request, response, exception);
      return null;
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
    ApiAuthenticationToken authenticatedToken = (ApiAuthenticationToken) authentication;
    String userId = authenticatedToken.getUser().getUserId();
    String role = authenticatedToken.getUser().getRole().getName();
    jwtService.setJwtCookies(response, userId, role);
    Response signInResponse = getResponse(
      request,
      OK,
      "You have signed in",
      null
    );
    this.responseWriter.write(response, OK, signInResponse);
  }
}
