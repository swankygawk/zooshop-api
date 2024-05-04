package ru.sfu.zooshop.dto.response;

public record Response(
    String timestamp,
    String path,
    Integer code,
    String status,
    String message,
    Object data
) {}
