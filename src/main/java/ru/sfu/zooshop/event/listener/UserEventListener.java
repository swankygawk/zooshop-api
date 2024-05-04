package ru.sfu.zooshop.event.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.event.UserEvent;
import ru.sfu.zooshop.service.EmailService;

@RequiredArgsConstructor
@Component
public class UserEventListener {
  private final EmailService emailService;

  @EventListener
  public void onUserEvent(UserEvent event) {
    UserEntity user = (UserEntity) event.getSource();
    switch (event.getEventType()) {
      case SIGN_UP -> emailService.sendVerificationMessage(
        user.getEmail(),
        user.getFirstName(),
        (String) event.getData().get("token")
      );
      case PASSWORD_RESET -> emailService.sendPasswordResetMessage(
        user.getEmail(),
        user.getFirstName(),
        (String) event.getData().get("token")
      );
      case EMAIL_UPDATE -> emailService.sendEmailUpdateMessage(
        (String) event.getData().get("newEmail"),
        user.getFirstName(),
        (String) event.getData().get("token")
      );
      default -> {}
    }
  }
}
