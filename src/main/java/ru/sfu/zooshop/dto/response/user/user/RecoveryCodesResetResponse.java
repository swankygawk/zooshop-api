package ru.sfu.zooshop.dto.response.user.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecoveryCodesResetResponse {
  private Set<String> newRecoveryCodes;
}
