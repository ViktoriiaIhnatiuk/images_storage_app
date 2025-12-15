package org.example.images_storage_app.dto.response;

import lombok.Data;

@Data
public class ImageLabelEntityResponseDTO {
    private String label;
    private float confidenceValue;
}
