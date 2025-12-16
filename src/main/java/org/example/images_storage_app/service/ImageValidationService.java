package org.example.images_storage_app.service;

import org.example.images_storage_app.exception.UnsupportedImageFormatException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Service
public class ImageValidationService {

    private static final Set<String> SUPPORTED_TYPES = Set.of(
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE
    );

    public void validate(MultipartFile file) {
        if (file.isEmpty()) {
            throw new UnsupportedImageFormatException("File is empty");
        }

        String contentType = file.getContentType();

        if (contentType == null || !SUPPORTED_TYPES.contains(contentType)) {
            throw new UnsupportedImageFormatException(
                    "Unsupported image type: " + contentType +
                    ". Only JPEG and PNG are allowed"
            );
        }
    }
}
