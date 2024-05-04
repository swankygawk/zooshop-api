package ru.sfu.zooshop.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.sfu.zooshop.dto.request.admin.role.RoleRequest;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.dto.response.admin.role.AllRolesResponse;
import ru.sfu.zooshop.dto.response.admin.role.RichRoleResponse;
import ru.sfu.zooshop.service.RoleService;

import java.net.URI;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/role")
@Validated
public class RoleController {
  private final RoleService roleService;

  private URI getRoleUri(Long id) {
    return URI.create("http://localhost:8080/api/v1/admin/role/" + id.toString());
  }

  @GetMapping
  @PreAuthorize("hasAuthority('ROLE:READ')")
  public ResponseEntity<Response> getRoles(
    HttpServletRequest request
  ) {
    AllRolesResponse roles = roleService.getRoles();
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Roles retrieved",
      roles
    ));
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE:READ')")
  public ResponseEntity<Response> getRole(
    HttpServletRequest request,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id
  ) {
    RichRoleResponse role = roleService.getRoleById(id);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Role retrieved",
      role
    ));
  }

  @PostMapping
  @PreAuthorize("hasAuthority('ROLE:CREATE')")
  public ResponseEntity<Response> createRole(
    HttpServletRequest request,
    @RequestBody @Valid RoleRequest roleRequest
  ) {
    Long id = roleService.createRole(roleRequest);
    return ResponseEntity.created(getRoleUri(id)).body(getResponse(
      request,
      CREATED,
      "Role created",
      null
    ));
  }

  @PatchMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE:UPDATE')")
  public ResponseEntity<Response> updateRole(
    HttpServletRequest request,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id,
    @RequestBody @Valid RoleRequest roleRequest
  ) {
    roleService.updateRole(id, roleRequest);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Role updated",
      null
    ));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE:DELETE')")
  public ResponseEntity<Response> deleteRole(
    HttpServletRequest request,
    @PathVariable("id") @PositiveOrZero(message = "ID must be greater or equal to 0") Long id
  ) {
    roleService.deleteRole(id);
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Role deleted",
      null
    ));
  }
}
