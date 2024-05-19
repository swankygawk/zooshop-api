package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.dto.request.user.address.AddressRequest;
import ru.sfu.zooshop.dto.response.user.address.AddressResponse;
import ru.sfu.zooshop.dto.response.user.address.AllAddressesResponse;
import ru.sfu.zooshop.entity.AddressEntity;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.UserMapper;
import ru.sfu.zooshop.repository.AddressRepository;
import ru.sfu.zooshop.service.AddressService;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class AddressServiceImpl implements AddressService {
  private final AddressRepository addressRepository;
  private final UserMapper userMapper;

  private AddressEntity findAddressById(Long id, Long userId) {
    return addressRepository.findByIdAndCreatedById(id, userId)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "Address with ID " + id + " not found"));
  }

  @Override
  public AllAddressesResponse getAddresses(Long userId) {
    List<AddressResponse> addresses = addressRepository.findAllByCreatedById(userId).stream()
      .map(userMapper::addressEntityToAddressResponse)
      .toList();
    return new AllAddressesResponse(addresses);
  }

  @Override
  public AddressResponse getAddress(Long id, Long userId) {
    AddressEntity address = findAddressById(id, userId);
    return userMapper.addressEntityToAddressResponse(address);
  }

  @Override
  public Long createAddress(AddressRequest request) {
    AddressEntity address = addressRepository.save(new AddressEntity(
      request.getCity(),
      request.getStreet(),
      request.getHouse(),
      request.getFlat()
    ));
    return address.getId();
  }

  @Override
  public void updateAddress(Long id, Long userId, AddressRequest request) {
    AddressEntity address = findAddressById(id, userId);
    address.setCity(request.getCity());
    address.setStreet(request.getStreet());
    address.setHouse(request.getHouse());
    address.setFlat(request.getFlat());
    addressRepository.save(address);
  }

  @Override
  public void deleteAddress(Long id, Long userId) {
    AddressEntity address = findAddressById(id, userId);
    addressRepository.delete(address);
  }
}
