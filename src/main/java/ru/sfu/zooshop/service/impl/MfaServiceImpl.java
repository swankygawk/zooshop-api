package ru.sfu.zooshop.service.impl;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.entity.RecoveryCodeEntity;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.repository.RecoveryCodeRepository;
import ru.sfu.zooshop.service.MfaService;

import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static ru.sfu.zooshop.utility.MfaUtility.generateRecoveryCodes;

@RequiredArgsConstructor
@Service
public class MfaServiceImpl implements MfaService {
  private static final CodeGenerator codeGenerator = new DefaultCodeGenerator();
  private static final TimeProvider timeProvider = new SystemTimeProvider();
  private static final CodeVerifier codeVerifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

  private final RecoveryCodeRepository recoveryCodeRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  private void validateOtp(UserEntity user, String otp) {
    if (!codeVerifier.isValidCode(user.getMfaSecret(), otp)) {
      throw new BadCredentialsException("Incorrect one-time password");
    }
  }

  private void validateRecoveryCode(UserEntity user, String code) {
    Set<RecoveryCodeEntity> recoveryCodes = recoveryCodeRepository.findAllByUser(user);
    if (recoveryCodes.isEmpty()) {
      throw new BadCredentialsException("No recovery codes available for this account. Please contact support team");
    }
    code = code.replace("-", "").toLowerCase();
    for (RecoveryCodeEntity recoveryCode : recoveryCodes) {
      if (passwordEncoder.matches(code, recoveryCode.getRecoveryCode())) {
        recoveryCodeRepository.delete(recoveryCode);
        return;
      }
    }
    throw new BadCredentialsException("Incorrect recovery code");
  }

  @Override
  public Set<String> createRecoveryCodes(UserEntity user) {
    Set<String> recoveryCodes = generateRecoveryCodes();
    recoveryCodeRepository.saveAll(
      recoveryCodes.stream()
      .map(recoveryCode -> recoveryCode.replace("-", ""))
      .map(passwordEncoder::encode)
      .map(recoveryCode -> new RecoveryCodeEntity(user, recoveryCode))
      .collect(toSet())
    );
    return recoveryCodes;
  }

  @Override
  public void deleteRecoveryCodes(UserEntity user) {
    recoveryCodeRepository.deleteAllByUser(user);
  }

  @Override
  public Set<String> resetRecoveryCodes(UserEntity user) {
    deleteRecoveryCodes(user);
    return createRecoveryCodes(user);
  }

  @Override
  public void validateMfaCredentials(UserEntity user, String otp, String recoveryCode) {
    if (!user.isMfaEnabled()) {
      return;
    }
    if (otp == null && recoveryCode == null) {
      throw new BadCredentialsException("You have MFA enabled. Please provide a one-time password from your authenticator app or a recovery code");
    }
    if (otp != null) {
      validateOtp(user, otp);
      return;
    }
    validateRecoveryCode(user, recoveryCode);
  }
}
