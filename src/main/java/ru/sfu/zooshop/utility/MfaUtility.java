package ru.sfu.zooshop.utility;

import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.recovery.RecoveryCodeGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import ru.sfu.zooshop.exception.ApiException;

import java.util.Set;
import java.util.stream.Collectors;

import static dev.samstevens.totp.code.HashingAlgorithm.SHA1;
import static dev.samstevens.totp.util.Utils.getDataUriForImage;
import static java.util.Arrays.stream;
import static ru.sfu.zooshop.constant.Constant.ZOOSHOP;

public final class MfaUtility {
  private static final SecretGenerator secretGenerator = new DefaultSecretGenerator();
  private static final ZxingPngQrGenerator pngGenerator = new ZxingPngQrGenerator();
  private static final RecoveryCodeGenerator recoveryCodeGenerator = new RecoveryCodeGenerator();

  private static QrData getQrCodeData(String email, String mfaSecret) {
    return new QrData.Builder()
      .issuer(ZOOSHOP)
      .label(email)
      .secret(mfaSecret)
      .algorithm(SHA1)
      .digits(6)
      .period(30)
      .build();
  }

  public static String generateMfaSecret() {
    return secretGenerator.generate();
  }

  public static Set<String> generateRecoveryCodes() {
    String[] codes = recoveryCodeGenerator.generateCodes(16);
    return stream(codes).collect(Collectors.toSet());
  }

  public static String generateMfaSetupQrCodeUri(String email, String mfaSecret) {
    QrData qrCodeData = getQrCodeData(email, mfaSecret);
    byte[] qrCodeImage;
    try {
      qrCodeImage = pngGenerator.generate(qrCodeData);
    } catch (Exception exception) {
      throw new ApiException("An error occurred while generating QR Code for MFA setup");
    }
    return getDataUriForImage(qrCodeImage, pngGenerator.getImageMimeType());
  }
}
