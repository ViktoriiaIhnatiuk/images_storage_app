package org.example.images_storage_app.mapper;

import org.example.images_storage_app.dto.request.ImageLabelEntityRequestDTO;
import org.example.images_storage_app.dto.response.ImageLabelEntityResponseDTO;
import org.example.images_storage_app.model.ImageLabelEntity;
import org.springframework.stereotype.Component;

@Component
public class ImageLabelEntityMapper {
    public ImageLabelEntityResponseDTO mapToDTO(ImageLabelEntity imageLabelEntity) {
        ImageLabelEntityResponseDTO imageLabelEntityResponseDTO = new ImageLabelEntityResponseDTO();
        imageLabelEntityResponseDTO.setLabel(imageLabelEntity.getLabel());
        imageLabelEntityResponseDTO.setConfidenceValue(imageLabelEntity.getConfidenceValue());
        return imageLabelEntityResponseDTO;
    }

    public ImageLabelEntity mapToObject(ImageLabelEntityRequestDTO imageLabelEntityRequestDTO) {
        ImageLabelEntity imageLabelEntity = new ImageLabelEntity();
        imageLabelEntity.setLabel(imageLabelEntityRequestDTO.getLabel());
        imageLabelEntity.setConfidenceValue(imageLabelEntityRequestDTO.getConfidenceValue());
        imageLabelEntity.setImage(imageLabelEntity.getImage());
        return imageLabelEntity;
    }

}
