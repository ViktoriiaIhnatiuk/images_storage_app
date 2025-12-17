package org.example.images_storage_app.dto.response;

import lombok.Data;

import java.util.List;
@Data
public class ImageEntityResponseDTO {
    private Long id;
    private String fileName;
    private String url;
    private String status;
    private List<ImageLabelEntityResponseDTO> imageLabels;
}
