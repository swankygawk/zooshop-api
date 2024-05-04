package ru.sfu.zooshop.dto.response.user.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllAddressesResponse {
  private List<AddressResponse> addresses;
}
