package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.dto.request.user.address.AddressRequest;
import ru.sfu.zooshop.entity.AddressEntity;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.repository.AddressRepository;
import ru.sfu.zooshop.service.AddressService;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class AddressServiceImpl implements AddressService {
  private final AddressRepository addressRepository;

  private void validateAddress(UserEntity user, AddressEntity address) {
    if (!Objects.equals(user.getId(), address.getUser().getId())) {
      throw new ApiException(FORBIDDEN, "This address does not belong to you");
    }
  }

  private AddressEntity getAddressById(Long id) {
    return addressRepository.findById(id)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Address with id " + id + " does not exist"));
  }

  @Override
  public List<AddressEntity> getAddresses(UserEntity user) {
    return addressRepository.findAllByUser(user);
  }

  @Override
  public AddressEntity getAddressById(UserEntity user, Long id) {
    AddressEntity address = getAddressById(id);
    validateAddress(user, address);
    return address;
  }

  @Override
  public Long createAddress(UserEntity user, AddressRequest request) {
    AddressEntity address = addressRepository.save(new AddressEntity(
      user,
      request.getCity(),
      request.getStreet(),
      request.getHouse(),
      request.getFlat()
    ));
    return address.getId();
  }

  @Override
  public void updateAddress(UserEntity user, Long id, AddressRequest request) {
    AddressEntity address = getAddressById(id);
    validateAddress(user, address);
    address.setCity(request.getCity());
    address.setStreet(request.getStreet());
    address.setHouse(request.getHouse());
    address.setFlat(request.getFlat());
    addressRepository.save(address);
  }

  @Override
  public void deleteAddress(UserEntity user, Long id) {
    AddressEntity address = getAddressById(id);
    validateAddress(user, address);
    addressRepository.delete(address);
  }
}
