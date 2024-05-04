package ru.sfu.zooshop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ru.sfu.zooshop.exception.ApiException;
import ru.sfu.zooshop.repository.UserRepository;
import ru.sfu.zooshop.service.EmailService;

import static org.springframework.http.HttpStatus.CONFLICT;
import static ru.sfu.zooshop.utility.EmailUtility.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
  private static final String VERIFICATION = "ZooShop Account Verification";
  private static final String PASSWORD_RESET = "ZooShop Password Reset";
  private static final String EMAIL_UPDATE = "ZooShop Account Email Update";

  private final UserRepository userRepository;
  private final JavaMailSender mailSender;

  @Value("${spring.mail.host}")
  private String host;

  @Value("${spring.mail.from}")
  private String from;

  @Override
  public void validateEmail(String email) {
    if (userRepository.existsByEmailIgnoreCase(email)) throw new ApiException(CONFLICT, "Email " + email + " is already taken");
  }

  @Override
  @Async
  public void sendVerificationMessage(String to, String name, String token) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setSubject(VERIFICATION);
      message.setFrom(from);
      message.setTo(to);
      message.setText(
        getVerificationMessage(host + ":3000", name, token)
      );
      mailSender.send(message);
    } catch (Exception exception) {
      log.error(exception.getMessage());
      throw new ApiException("Error sending verification message");
    }
  }

  @Override
  @Async
  public void sendPasswordResetMessage(String to, String name, String token) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setSubject(PASSWORD_RESET);
      message.setFrom(from);
      message.setTo(to);
      message.setText(
        getPasswordResetMessage(host + ":3000", name, token)
      );
      mailSender.send(message);
    } catch (Exception exception) {
      log.error(exception.getMessage());
      throw new ApiException("Error sending password reset message");
    }
  }

  @Override
  @Async
  public void sendEmailUpdateMessage(String to, String name, String token) {
    try {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setSubject(EMAIL_UPDATE);
      message.setFrom(from);
      message.setTo(to);
      message.setText(
        getEmailUpdateMessage(host + ":3000", name, token)
      );
      mailSender.send(message);
    } catch (Exception exception) {
      log.error(exception.getMessage());
      throw new ApiException("Error sending email update message");
    }
  }
}
