package ru.sfu.zooshop.controller.open;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sfu.zooshop.dto.request.open.user.EmailRequest;
import ru.sfu.zooshop.dto.request.open.user.PasswordResetRequest;
import ru.sfu.zooshop.dto.request.open.user.SignUpRequest;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.service.AuthService;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
  private final AuthService authService;

  private URI getUri() {
    return URI.create("http://localhost:8080/api/v1/user/profile");
  }

  @PostMapping("/signUp")
  public ResponseEntity<Response> signUp(
    HttpServletRequest request,
    @RequestBody @Valid SignUpRequest signUpRequest
  ) {
    authService.signUp(signUpRequest);
    return ResponseEntity.created(getUri()).body(getResponse(
      request,
      CREATED,
      "Account created. A verification link has been sent to your email",
      null
    ));
  }

  @PatchMapping("/verify")
  public ResponseEntity<Response> verifyByEmail(
    HttpServletRequest request,
    @RequestParam("token") @NotBlank(message = "Token must not be empty") String token
  ) {
    authService.verifyByEmail(token);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Your account has been verified",
      null
    ));
  }

  @PostMapping("/verify/resend")
  public ResponseEntity<Response> resendVerificationLink(
    HttpServletRequest request,
    @RequestBody @Valid EmailRequest emailRequest
  ) {
    authService.resendVerificationToken(emailRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "A verification link has been sent to your email",
      null
    ));
  }

  @PostMapping("/reset")
  public ResponseEntity<Response> requestPasswordReset(
    HttpServletRequest request,
    @RequestBody @Valid EmailRequest emailRequest
  ) {
    authService.requestPasswordReset(emailRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "A password reset link has been sent to your email",
      null
    ));
  }

  @PostMapping("/reset/resend")
  public ResponseEntity<Response> resendPasswordResetLink(
    HttpServletRequest request,
    @RequestBody @Valid EmailRequest emailRequest
  ) {
    return requestPasswordReset(request, emailRequest);
  }

  @PatchMapping("/reset")
  public ResponseEntity<Response> resetPassword(
    HttpServletRequest request,
    @RequestParam("token") @NotBlank(message = "Token must not be empty") String token,
    @RequestBody @Valid PasswordResetRequest passwordResetRequest
  ) {
    authService.resetPassword(token, passwordResetRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Your password has been reset. You can now use new password to sign in",
      null
    ));
  }
}
