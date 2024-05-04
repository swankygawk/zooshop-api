package ru.sfu.zooshop.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.user.user.EmailUpdateRequest;
import ru.sfu.zooshop.dto.request.user.user.PasswordUpdateRequest;
import ru.sfu.zooshop.dto.request.user.user.ProfileRequest;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.dto.response.user.user.ProfileResponse;
import ru.sfu.zooshop.security.user.AuthenticatedUser;
import ru.sfu.zooshop.service.UserService;

import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  @GetMapping("/profile")
  public ResponseEntity<Response> getProfile(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user
  ) {
    ProfileResponse profile = userService.getProfile(user.getId());
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Profile retrieved",
      profile
    ));
  }

  @PatchMapping("/update/profile")
  public ResponseEntity<Response> updateProfile(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @RequestBody @Valid ProfileRequest profileUpdateRequest
  ) {
    userService.updateProfile(user.getId(), profileUpdateRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Profile updated",
      null
    ));
  }

  @PostMapping("/update/email")
  public ResponseEntity<Response> requestEmailUpdate(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @RequestBody @Valid EmailUpdateRequest emailUpdateRequest
    ) {
    userService.requestEmailUpdate(user.getId(), emailUpdateRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "A verification link has been sent to your new email",
      null
    ));
  }

  @PostMapping("/update/email/resend")
  public ResponseEntity<Response> resendEmailUpdateLink(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @RequestBody @Valid EmailUpdateRequest emailUpdateRequest
  ) {
    return requestEmailUpdate(request, user, emailUpdateRequest);
  }

  @PatchMapping("/update/email")
  public ResponseEntity<Response> updateEmail(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @RequestParam("token") @NotBlank(message = "Token must not be empty") String token
  ) {
    userService.updateEmail(user.getId(), token);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Email updated",
      null
    ));
  }

  @PatchMapping("/update/password")
  public ResponseEntity<Response> updatePassword(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest
  ) {
    userService.updatePassword(user.getId(), passwordUpdateRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Password updated",
      null
    ));
  }

  @PutMapping("/update/picture")
  public ResponseEntity<Response> updateProfilePicture(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @RequestParam MultipartFile file
  ) {
    userService.updateProfilePicture(user.getId(), file);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Profile picture updated",
      null
    ));
  }

  @DeleteMapping("/update/picture")
  public ResponseEntity<Response> deleteProfilePicture(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user
  ) {
    userService.deleteProfilePicture(user.getId());
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Profile picture deleted",
      null
    ));
  }
}
