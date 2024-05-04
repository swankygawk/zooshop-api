package ru.sfu.zooshop.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sfu.zooshop.dto.request.user.user.ActionConfirmationRequest;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.dto.response.user.user.MfaEnabledResponse;
import ru.sfu.zooshop.dto.response.user.user.RecoveryCodesResetResponse;
import ru.sfu.zooshop.security.user.AuthenticatedUser;
import ru.sfu.zooshop.service.UserService;

import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/mfa")
public class MfaController {
  private final UserService userService;

  @PatchMapping("/enable")
  @PreAuthorize("hasAuthority('USER:UPDATE_OWN')")
  public ResponseEntity<Response> enableMfa(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @RequestBody @Valid ActionConfirmationRequest confirmation
  ) {
    MfaEnabledResponse response = userService.enableMfa(user.getId(), confirmation);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "MFA enabled",
      response
    ));
  }

  @PatchMapping("/disable")
  @PreAuthorize("hasAuthority('USER:UPDATE_OWN')")
  public ResponseEntity<Response> disableMfa(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @RequestBody @Valid ActionConfirmationRequest confirmation
  ) {
    userService.disableMfa(user.getId(), confirmation);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "MFA disabled",
      null
    ));
  }

  @PatchMapping("/reset")
  @PreAuthorize("hasAuthority('USER:UPDATE_OWN')")
  public ResponseEntity<Response> resetRecoveryCodes(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @RequestBody @Valid ActionConfirmationRequest confirmation
  ) {
    RecoveryCodesResetResponse response = userService.resetRecoveryCodes(user.getId(), confirmation);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Recovery codes reset",
      response
    ));
  }
}
