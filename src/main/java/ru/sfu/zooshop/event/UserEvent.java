package ru.sfu.zooshop.event;

import lombok.Getter;
import lombok.Setter;
import ru.sfu.zooshop.entity.UserEntity;
import ru.sfu.zooshop.enumeration.UserEventType;

import java.util.Map;

@Getter
@Setter
public class UserEvent extends BaseEvent<UserEntity> {
  private UserEventType eventType;

  public UserEvent(UserEntity user, UserEventType eventType, Map<?, ?> data) {
    super(user, data);
    this.eventType = eventType;
  }
}
