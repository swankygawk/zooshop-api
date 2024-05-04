package ru.sfu.zooshop.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Map;

@Getter
@Setter
public abstract class BaseEvent<T> extends ApplicationEvent {
  protected Map<?, ?> data;

  public BaseEvent(T source, Map<?, ?> data) {
    super(source);
    this.data = data;
  }
}
