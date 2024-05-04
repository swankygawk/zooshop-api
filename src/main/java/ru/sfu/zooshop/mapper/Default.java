package ru.sfu.zooshop.mapper;

import java.time.OffsetDateTime;

public interface Default {
  default String map(OffsetDateTime value) {
    return value == null ? null : value.toString();
  }
}
