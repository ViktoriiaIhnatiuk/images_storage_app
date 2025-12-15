package org.example.images_storage_app.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class ImageEntityRequestDTO {
    private Long id;
    private String fileName;
    private List<ImageLabelEntityRequestDTO> imageLabels;
}
