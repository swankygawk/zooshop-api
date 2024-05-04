package ru.sfu.zooshop.service;

import ru.sfu.zooshop.dto.request.user.address.AddressRequest;
import ru.sfu.zooshop.entity.AddressEntity;
import ru.sfu.zooshop.entity.UserEntity;

import java.util.List;

public interface AddressService {
  List<AddressEntity> getAddresses(UserEntity user);
  AddressEntity getAddressById(UserEntity user, Long id);
  Long createAddress(UserEntity user, AddressRequest request);
  void updateAddress(UserEntity user, Long id, AddressRequest request);
  void deleteAddress(UserEntity user, Long id);
}
