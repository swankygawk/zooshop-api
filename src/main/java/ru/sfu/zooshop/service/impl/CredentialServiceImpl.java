package ru.sfu.zooshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.entity.CredentialEntity;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.repository.CredentialRepository;
import ru.sfu.zooshop.service.CredentialService;

@RequiredArgsConstructor
@Service
@Transactional(rollbackOn = Exception.class)
public class CredentialServiceImpl implements CredentialService {
  private final CredentialRepository credentialRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Override
  public CredentialEntity createCredential(String rawPassword) {
    CredentialEntity credential = new CredentialEntity(passwordEncoder.encode(rawPassword));
    return credentialRepository.save(credential);
  }

  @Override
  public void validateCredential(UserEntity user, String rawPassword) {
    if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
      throw new BadCredentialsException("Incorrect password");
    }
  }

  @Override
  public void updateCredential(UserEntity user, String rawNewPassword) {
    CredentialEntity credential = user.getCredential();
    credential.setPassword(passwordEncoder.encode(rawNewPassword));
    credentialRepository.save(credential);
  }
}
