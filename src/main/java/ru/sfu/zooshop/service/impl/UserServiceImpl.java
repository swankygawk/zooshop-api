package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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
import ru.sfu.zooshop.entity.AddressEntity;
import ru.sfu.zooshop.entity.TokenEntity;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.event.UserEvent;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.mapper.user.UserMapper;
import ru.sfu.zooshop.repository.UserRepository;
import ru.sfu.zooshop.service.*;
import ru.sfu.zooshop.service.EmailService;
import ru.sfu.zooshop.service.FileService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;
import static ru.sfu.zooshop.constant.Constant.PROFILE_PICTURE_STORAGE_LOCATION;
import static ru.sfu.zooshop.enumeration.FileType.PROFILE_PICTURE;
import static ru.sfu.zooshop.enumeration.UserEventType.EMAIL_UPDATE;
import static ru.sfu.zooshop.utility.MfaUtility.generateMfaSecret;
import static ru.sfu.zooshop.utility.MfaUtility.generateMfaSetupQrCodeUri;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final CredentialService credentialService;
  private final EmailService emailService;
  private final TokenService tokenService;
  private final MfaService mfaService;
  private final AddressService addressService;
  private final FileService fileService;
  private final ApplicationEventPublisher eventPublisher;
  private final UserMapper userMapper;

  @Override
  public UserEntity getSystem() {
    return userRepository.findById(0L)
      .orElseThrow(() -> new ApiException("System does not exist"));
  }

  @Override
  public UserEntity findUserById(Long id) {
    return userRepository.findById(id)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "User with id " + id + " does not exist"));
  }

  @Override
  public UserEntity findUserByUserId(String userId) {
    return userRepository.findByUserId(userId)
      .orElseThrow(() -> new ApiException(NOT_FOUND, "User with userId " + userId + " does not exist"));
  }

  @Override
  public UserEntity findUserByEmail(String email) {
    return userRepository.findByEmailIgnoreCase(email)
      .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " does not exist"));
  }

  @Override
  public void validateAccount(UserEntity user) {
    if (user.isTemporarilyLocked()) throw new ApiException(FORBIDDEN, "Your account is temporarily locked. Please wait for 15 minutes to sign in");
    if (!user.isAccountNonLocked()) throw new LockedException("Your account is locked");
    if (!user.isEnabled()) throw new DisabledException("Your account is disabled. Please use the verification link we sent you to enable your account");
    if (!user.isCredentialsNonExpired()) throw new CredentialsExpiredException("Your password is expired. Please reset your password");
  }

  @Override
  public MfaEnabledResponse enableMfa(Long id, ActionConfirmationRequest request) {
    UserEntity user = findUserById(id);
    credentialService.validateCredential(user, request.getPassword());
    if (user.isMfaEnabled()) throw new ApiException(BAD_REQUEST, "MFA is already enabled");
    String mfaSecret = generateMfaSecret();
    user.setMfaSecret(mfaSecret);
    user.setMfaEnabled(true);
    user = userRepository.save(user);
    String mfaSetupQrCodeUri = generateMfaSetupQrCodeUri(user.getEmail(), mfaSecret);
    Set<String> recoveryCodes = mfaService.createRecoveryCodes(user);
    return new MfaEnabledResponse(mfaSetupQrCodeUri, mfaSecret, recoveryCodes);
  }

  @Override
  public void disableMfa(Long id, ActionConfirmationRequest request) {
    UserEntity user = findUserById(id);
    credentialService.validateCredential(user, request.getPassword());
    if (!user.isMfaEnabled()) throw new ApiException(BAD_REQUEST, "MFA is already disabled");
    user.setMfaSecret(null);
    user.setMfaEnabled(false);
    user = userRepository.save(user);
    mfaService.deleteRecoveryCodes(user);
  }

  @Override
  public RecoveryCodesResetResponse resetRecoveryCodes(Long id, ActionConfirmationRequest request) {
    UserEntity user = findUserById(id);
    credentialService.validateCredential(user, request.getPassword());
    if (!user.isMfaEnabled()) throw new ApiException(BAD_REQUEST, "MFA is not enabled");
    Set<String> newRecoveryCodes = mfaService.resetRecoveryCodes(user);
    return new RecoveryCodesResetResponse(newRecoveryCodes);
  }

  @Override
  public ProfileResponse getProfile(Long id) {
    UserEntity user = findUserById(id);
    return userMapper.userEntityToProfileResponse(user);
  }

  @Override
  public void requestEmailUpdate(Long id, EmailUpdateRequest request) {
    UserEntity user = findUserById(id);
    credentialService.validateCredential(user, request.getPassword());
    emailService.validateEmail(request.getNewEmail());
    TokenEntity emailUpdateToken = tokenService.getOrCreate(user, request.getNewEmail());
    eventPublisher.publishEvent(
      new UserEvent(
        user,
        EMAIL_UPDATE,
        Map.of("newEmail", emailUpdateToken.getAdditionalData(),"token", emailUpdateToken.getValue())
      )
    );
  }

  @Override
  public void updateEmail(Long id, String token) {
    TokenEntity emailUpdateToken = tokenService.getTokenByValue(token);
    if (!Objects.equals(id, emailUpdateToken.getUser().getId())) throw new ApiException(FORBIDDEN, "This token does not belong to you");
    UserEntity user = emailUpdateToken.getUser();
    user.setEmail(emailUpdateToken.getAdditionalData());
    userRepository.save(user);
    tokenService.deleteToken(emailUpdateToken);
  }

  @Override
  public void updatePassword(Long id, PasswordUpdateRequest request) {
    UserEntity user = findUserById(id);
    credentialService.validateCredential(user, request.getOldPassword());
    credentialService.updateCredential(user, request.getNewPassword());
  }

  @Override
  public void updateProfilePicture(Long id, MultipartFile file) {
    UserEntity user = findUserById(id);
    String fileName = user.getReferenceId() + ".png";
    String profilePictureUrl = fileService.saveFile(file, PROFILE_PICTURE_STORAGE_LOCATION, fileName, PROFILE_PICTURE);
    if (user.getProfilePictureUrl() == null) {
      user.setProfilePictureUrl(profilePictureUrl);
      userRepository.save(user);
    }
  }

  @Override
  public void deleteProfilePicture(Long id) {
    UserEntity user = findUserById(id);
    if (user.getProfilePictureUrl() == null) throw new ApiException(BAD_REQUEST, "You do not have a profile picture");
    user.setProfilePictureUrl(null);
    userRepository.save(user);
    fileService.deleteFile(PROFILE_PICTURE_STORAGE_LOCATION, user.getReferenceId() + ".png");
  }

  @Override
  public void updateProfile(Long id, ProfileRequest request) {
    UserEntity user = findUserById(id);
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setPhone(request.getPhone());
    userRepository.save(user);
  }

  @Override
  public AllAddressesResponse getAddresses(Long id) {
    UserEntity user = findUserById(id);
    List<AddressResponse> addresses = addressService.getAddresses(user).stream()
      .map(userMapper::addressEntityToAddressResponse)
      .toList();
    return new AllAddressesResponse(addresses);
  }

  @Override
  public AddressResponse getAddress(Long id, Long addressId) {
    UserEntity user = findUserById(id);
    AddressEntity address = addressService.getAddressById(user, addressId);
    return userMapper.addressEntityToAddressResponse(address);
  }

  @Override
  public Long createAddress(Long id, AddressRequest request) {
    UserEntity user = findUserById(id);
    return addressService.createAddress(user, request);
  }

  @Override
  public void updateAddress(Long id, Long addressId, AddressRequest request) {
    UserEntity user = findUserById(id);
    addressService.updateAddress(user, addressId, request);
  }

  @Override
  public void deleteAddress(Long id, Long addressId) {
    UserEntity user = findUserById(id);
    addressService.deleteAddress(user, addressId);
  }
}
