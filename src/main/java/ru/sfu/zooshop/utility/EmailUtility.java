package ru.sfu.zooshop.utility;

public final class EmailUtility {
  private static String getVerificationUrl(String host, String token) {
    return host + "/auth/verify?token=" + token;
  }

  private static String getPasswordResetUrl(String host, String token) {
    return host + "/auth/reset?token=" + token;
  }

  private static String getEmailUpdateUrl(String host, String token) { return host + "/user/update/email?token=" + token; }

  public static String getVerificationMessage(String host, String name, String token) {
    return "Hello " + name + ",\n\n" + "Your account has been created. Please click on the link below to verify your account.\n\n" +
        getVerificationUrl(host, token) + "\n\n" + "ZooShop Support Team";
  }

  public static String getPasswordResetMessage(String host, String name, String token) {
    return "Hello " + name + ",\n\n" + "Your have requested to reset your password. Please click on the link below to set a new password.\nIf it wasn't you, simply ignore this message.\n\n" +
        getPasswordResetUrl(host, token) + "\n\n" + "ZooShop Support Team";
  }

  public static String getEmailUpdateMessage(String host, String name, String token) {
    return "Hello " + name + ",\n\n" + "You have requested to update your email. Please click on the link below to confirm it.\nIf it wasn't you, simply ignore this message.\n\n" + getEmailUpdateUrl(host, token) + "\n\n" + "ZooShop Support Team";
  }
}
