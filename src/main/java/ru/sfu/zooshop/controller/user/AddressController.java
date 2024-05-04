package ru.sfu.zooshop.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sfu.zooshop.dto.request.user.address.AddressRequest;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.dto.response.user.address.AddressResponse;
import ru.sfu.zooshop.dto.response.user.address.AllAddressesResponse;
import ru.sfu.zooshop.security.user.AuthenticatedUser;
import ru.sfu.zooshop.service.UserService;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user/address")
@Validated
public class AddressController {
  private final UserService userService;

  private URI getAddressUri(Long id) {
    return URI.create("http://localhost:8080/api/v1/user/address/" + id.toString());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ADDRESS:READ_OWN')")
  public ResponseEntity<Response> getAddresses(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user
  ) {
    AllAddressesResponse addresses = userService.getAddresses(user.getId());
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Addresses retrieved",
      addresses
    ));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ADDRESS:READ_OWN')")
  public ResponseEntity<Response> getAddress(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id
  ) {
    AddressResponse address = userService.getAddress(user.getId(), id);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Address retrieved",
      address
    ));
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ADDRESS:CREATE_OWN')")
  public ResponseEntity<Response> createAddress(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @RequestBody @Valid AddressRequest address
  ) {
    Long id = userService.createAddress(user.getId(), address);
    return ResponseEntity.created(getAddressUri(id)).body(getResponse(
      request,
      CREATED,
      "Address created",
      null
    ));
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasAuthority('ADDRESS:UPDATE_OWN')")
  public ResponseEntity<Response> updateAddress(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id,
    @RequestBody @Valid AddressRequest address
  ) {
    userService.updateAddress(user.getId(), id, address);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Address updated",
      null
    ));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ADDRESS:DELETE_OWN')")
  public ResponseEntity<Response> deleteAddress(
    HttpServletRequest request,
    @AuthenticationPrincipal AuthenticatedUser user,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id
  ) {
    userService.deleteAddress(user.getId(), id);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Address deleted",
      null
    ));
  }
}
