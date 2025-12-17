package org.example.images_storage_app.dto.response;

public record ErrorResponse(
    String code,
    String message,
    String details
) {}
