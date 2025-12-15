package org.example.images_storage_app.dto.response;

import lombok.Data;
import org.example.images_storage_app.dto.request.ImageLabelEntityRequestDTO;

import java.util.List;
@Data
public class ImageEntityResponseDTO {
    private Long id;
    private String fileName;
    private List<ImageLabelEntityResponseDTO> imageLabels;
}
