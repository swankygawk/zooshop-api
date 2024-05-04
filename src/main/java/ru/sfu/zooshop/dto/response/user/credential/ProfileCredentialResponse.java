package ru.sfu.zooshop.dto.response.user.credential;

import lombok.*;
import ru.sfu.zooshop.dto.response.user.UserAuditResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCredentialResponse extends UserAuditResponse {
  private String expiresAt;
}
