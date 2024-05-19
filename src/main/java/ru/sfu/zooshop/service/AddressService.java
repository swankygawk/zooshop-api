package ru.sfu.zooshop.service;

import ru.sfu.zooshop.dto.request.user.address.AddressRequest;
import ru.sfu.zooshop.dto.response.user.address.AddressResponse;
import ru.sfu.zooshop.dto.response.user.address.AllAddressesResponse;

public interface AddressService {
  AllAddressesResponse getAddresses(Long userId);
  AddressResponse getAddress(Long id, Long userId);
  Long createAddress(AddressRequest request);
  void updateAddress(Long id, Long userId, AddressRequest request);
  void deleteAddress(Long id, Long userId);
}
