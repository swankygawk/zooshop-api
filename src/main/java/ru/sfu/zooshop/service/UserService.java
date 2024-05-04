package ru.sfu.zooshop.service;

import org.springframework.web.multipart.MultipartFile;
import ru.sfu.zooshop.dto.request.user.user.ActionConfirmationRequest;
import ru.sfu.zooshop.dto.request.user.user.EmailUpdateRequest;
import ru.sfu.zooshop.dto.request.user.user.PasswordUpdateRequest;
import ru.sfu.zooshop.dto.request.user.user.ProfileRequest;
import ru.sfu.zooshop.dto.request.user.address.AddressRequest;
import ru.sfu.zooshop.dto.response.user.address.AddressResponse;
import ru.sfu.zooshop.dto.response.user.address.AllAddressesResponse;
import ru.sfu.zooshop.dto.response.user.user.MfaEnabledResponse;
import ru.sfu.zooshop.dto.response.user.user.RecoveryCodesResetResponse;
import ru.sfu.zooshop.dto.response.user.user.ProfileResponse;
import ru.sfu.zooshop.entity.UserEntity;

public interface UserService {
  UserEntity getSystem();
  UserEntity findUserById(Long id);
  UserEntity findUserByUserId(String userId);
  UserEntity findUserByEmail(String email);
  void validateAccount(UserEntity user);
  MfaEnabledResponse enableMfa(Long id, ActionConfirmationRequest request);
  void disableMfa(Long id, ActionConfirmationRequest request);
  RecoveryCodesResetResponse resetRecoveryCodes(Long id, ActionConfirmationRequest request);
  ProfileResponse getProfile(Long id);
  void requestEmailUpdate(Long id, EmailUpdateRequest request);
  void updateEmail(Long id, String token);
  void updatePassword(Long id, PasswordUpdateRequest request);
  void updateProfilePicture(Long id, MultipartFile file);
  void deleteProfilePicture(Long id);
  void updateProfile(Long id, ProfileRequest request);
  AllAddressesResponse getAddresses(Long id);
  AddressResponse getAddress(Long id, Long addressId);
  Long createAddress(Long id, AddressRequest request);
  void updateAddress(Long id, Long addressId, AddressRequest request);
  void deleteAddress(Long id, Long addressId);
}
