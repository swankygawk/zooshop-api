package ru.sfu.zooshop.service;

public interface EmailService {
  void validateEmail(String email);
  void sendVerificationMessage(String to, String name, String token);
  void sendPasswordResetMessage(String to, String name, String token);
  void sendEmailUpdateMessage(String to, String name, String token);
}
