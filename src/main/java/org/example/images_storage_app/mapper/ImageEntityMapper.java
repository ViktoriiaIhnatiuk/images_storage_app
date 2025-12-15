package org.example.images_storage_app.mapper;

import org.example.images_storage_app.dto.request.ImageEntityRequestDTO;
import org.example.images_storage_app.dto.response.ImageEntityResponseDTO;
import org.example.images_storage_app.model.ImageEntity;
import org.example.images_storage_app.repository.ImageLabelRepository;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ImageEntityMapper {
    private final ImageLabelRepository imageLabelRepository;
    private final ImageLabelEntityMapper imageLabelEntityMapper;

    public ImageEntityMapper(ImageLabelRepository imageLabelRepository, ImageLabelEntityMapper imageLabelEntityMapper) {
        this.imageLabelRepository = imageLabelRepository;
        this.imageLabelEntityMapper = imageLabelEntityMapper;
    }

    public ImageEntity mapToObject(ImageEntityRequestDTO imageEntityRequestDTO) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setFileName(imageEntityRequestDTO.getFileName());
        return  imageEntity;
    }

    public ImageEntityResponseDTO mapToDTO(ImageEntity  imageEntity) {
        ImageEntityResponseDTO imageEntityResponseDTO = new ImageEntityResponseDTO();
        imageEntityResponseDTO.setFileName(imageEntity.getFileName());
        imageEntityResponseDTO.setImageLabels(imageLabelRepository.getImageLabelEntitiesByImage(imageEntity).stream().map(imageLabelEntityMapper::mapToDTO).collect(Collectors.toList()));
        return imageEntityResponseDTO;
    }
}
