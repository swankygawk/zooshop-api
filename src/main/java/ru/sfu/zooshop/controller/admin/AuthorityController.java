package ru.sfu.zooshop.controller.admin;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sfu.zooshop.dto.response.Response;
import ru.sfu.zooshop.dto.response.admin.authority.AllAuthoritiesResponse;
import ru.sfu.zooshop.service.AuthorityService;

import static org.springframework.http.HttpStatus.OK;
import static ru.sfu.zooshop.utility.ResponseUtility.getResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/authority")
public class AuthorityController {
  private final AuthorityService authorityService;

  @GetMapping
  @PreAuthorize("hasAuthority('AUTHORITY:READ')")
  public ResponseEntity<Response> getAuthorities(
    HttpServletRequest request
  ) {
    AllAuthoritiesResponse authorities = authorityService.getAuthorities();
    return ResponseEntity.ok(getResponse(
      request,
      OK,
      "Authorities retrieved",
      authorities
    ));
  }
}
