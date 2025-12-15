package org.example.images_storage_app.dto.request;

import lombok.Data;

@Data
public class ImageLabelEntityRequestDTO {
    private String label;
    private float confidenceValue;
}
