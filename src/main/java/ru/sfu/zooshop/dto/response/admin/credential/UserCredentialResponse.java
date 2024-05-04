package ru.sfu.zooshop.dto.response.admin.credential;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfu.zooshop.dto.response.admin.AdminAuditResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCredentialResponse extends AdminAuditResponse {
  private String expiresAt;
  private boolean expired;
}
