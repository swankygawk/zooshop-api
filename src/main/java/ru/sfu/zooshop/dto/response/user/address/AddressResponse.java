package ru.sfu.zooshop.dto.response.user.address;

import lombok.*;
import ru.sfu.zooshop.dto.response.user.UserAuditResponse;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponse extends UserAuditResponse {
  private Long id;
  private String city;
  private String street;
  private String house;
  private Integer flat;
}
