package org.example.images_storage_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedImageFormatException extends RuntimeException {

    public UnsupportedImageFormatException(String message) {
        super(message);
    }
}
