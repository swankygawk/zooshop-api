package ru.sfu.zooshop.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sfu.zooshop.dto.request.admin.role.UserRoleUpdateRequest;
import ru.sfu.zooshop.dto.request.admin.user.UserEmailUpdateRequest;
import ru.sfu.zooshop.dto.request.admin.user.UserProfileUpdateRequest;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.dto.response.admin.user.AllUsersResponse;
import ru.sfu.zooshop.dto.response.admin.user.RichUserResponse;
import ru.sfu.zooshop.security.user.AuthenticatedUser;
import ru.sfu.zooshop.service.admin.AdminUserService;

import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/user")
@Validated
public class AdminUserController {
  private final AdminUserService adminUserService;

  @GetMapping
  @PreAuthorize("hasAuthority('USER:READ')")
  public ResponseEntity<Response> getAll(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
    Pageable pageable
  ) {
    AllUsersResponse users = adminUserService.getUsers(authenticatedUser.getId(), pageable);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Users retrieved",
      users
    ));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('USER:READ')")
  public ResponseEntity<Response> get(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id
  ) {
    RichUserResponse user = adminUserService.getUserById(authenticatedUser.getId(), id);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "User retrieved",
      user
    ));
  }

  @PatchMapping("/{id}/update/profile")
  @PreAuthorize("hasAuthority('USER:UPDATE')")
  public ResponseEntity<Response> updateProfile(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id,
    @RequestBody @Valid UserProfileUpdateRequest userProfileUpdateRequest
    ) {
    adminUserService.updateUserProfile(authenticatedUser.getId(), id, userProfileUpdateRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "User's profile updated",
      null
    ));
  }

  @PatchMapping("/{id}/update/email")
  @PreAuthorize("hasAuthority('USER:UPDATE')")
  public ResponseEntity<Response> updateEmail(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id,
    @RequestBody @Valid UserEmailUpdateRequest userEmailUpdateRequest
    ) {
    adminUserService.updateUserEmail(authenticatedUser.getId(), id, userEmailUpdateRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "User's email updated",
      null
    ));
  }

  @DeleteMapping("/{id}/update/picture")
  @PreAuthorize("hasAuthority('USER:UPDATE')")
  public ResponseEntity<Response> deleteProfilePicture(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id
    ) {
    adminUserService.deleteUserProfilePicture(authenticatedUser.getId(), id);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "User's profile productpicture deleted",
      null
    ));
  }

  @PatchMapping("/{id}/lock")
  @PreAuthorize("hasAuthority('USER:UPDATE')")
  public ResponseEntity<Response> lockAccount(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id
  ) {
    adminUserService.toggleUserAccountLock(authenticatedUser.getId(), id, true);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "User's account locked",
      null
    ));
  }

  @PatchMapping("/{id}/unlock")
  @PreAuthorize("hasAuthority('USER:UPDATE')")
  public ResponseEntity<Response> unlockAccount(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id
  ) {
    adminUserService.toggleUserAccountLock(authenticatedUser.getId(), id, false);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "User's account unlocked",
      null
    ));
  }

  @PatchMapping("/{id}/mfa/disable")
  @PreAuthorize("hasAuthority('USER:UPDATE')")
  public ResponseEntity<Response> disableMfa(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id
  ) {
    adminUserService.disableUserMfa(authenticatedUser.getId(), id);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "User's MFA disabled",
      null
    ));
  }

  @PatchMapping("/{id}/update/role")
  @PreAuthorize("hasAuthority('USER:UPDATE')")
  public ResponseEntity<Response> updateRole(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id,
    @RequestBody @Valid UserRoleUpdateRequest userRoleUpdateRequest
  ) {
    adminUserService.updateUserRole(authenticatedUser.getId(), id, userRoleUpdateRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "User's role updated",
      null
    ));
  }

  @GetMapping("/{id}/address")
  @PreAuthorize("hasAuthority('ADDRESS:READ')")
  public ResponseEntity<Response> getAddresses(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id
    ) {
    return null;
  }

  // favorite products

  // orders

  // ratings

  // comments
}
