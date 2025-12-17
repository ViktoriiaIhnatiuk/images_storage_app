package org.example.images_storage_app.exception;

import org.example.images_storage_app.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnsupportedImageFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUnsupportedFormat(UnsupportedImageFormatException ex) {
        return new ErrorResponse(
            "UNSUPPORTED_FILE_TYPE",
            ex.getMessage(),
            "Allowed formats: jpg, jpeg, png"
        );
    }

    @ExceptionHandler(ImageAnalysisException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleAnalysis(ImageAnalysisException ex) {
        return new ErrorResponse(
            "REKOGNITION_FAILED",
            "Image cannot be analyzed",
            ex.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ex) {
        return new ErrorResponse(
            "INTERNAL_ERROR",
            "Unexpected server error",
            null
        );
    }
}
