package org.example.images_storage_app.mapper;

import org.example.images_storage_app.dto.request.ImageEntityRequestDTO;
import org.example.images_storage_app.dto.response.ImageEntityResponseDTO;
import org.example.images_storage_app.model.ImageEntity;
import org.example.images_storage_app.model.ImageStatus;
import org.example.images_storage_app.service.S3PresignedUrlService;
import org.springframework.stereotype.Component;

@Component
public class ImageEntityMapper {
    private final ImageLabelEntityMapper imageLabelEntityMapper;
    private final S3PresignedUrlService presignedUrlService;
    private final String BUCKET_NAME = "images-storage-app-bucket-west-region";


    public ImageEntityMapper(ImageLabelEntityMapper imageLabelEntityMapper, S3PresignedUrlService presignedUrlService) {
        this.imageLabelEntityMapper = imageLabelEntityMapper;
        this.presignedUrlService = presignedUrlService;
    }

    public ImageEntity mapToObject(ImageEntityRequestDTO imageEntityRequestDTO) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setFileName(imageEntityRequestDTO.getFileName());
        return  imageEntity;
    }

    public ImageEntityResponseDTO mapToDTO(ImageEntity  imageEntity) {
        ImageEntityResponseDTO imageEntityResponseDTO = new ImageEntityResponseDTO();
        imageEntityResponseDTO.setFileName(imageEntity.getFileName());
        imageEntityResponseDTO.setStatus(imageEntity.getStatus().name());
        if (imageEntity.getStatus() == ImageStatus.READY) {
            imageEntityResponseDTO.setUrl(presignedUrlService.generateUrl(
                    BUCKET_NAME,
                    imageEntity.getFileName()
            ));
        }
        if (imageEntity.getImageLabels() != null) {
            imageEntityResponseDTO.setImageLabels(
                    imageEntity.getImageLabels()
                            .stream()
                            .map(imageLabelEntityMapper::mapToDTO)
                            .toList()
            );
        }
        return imageEntityResponseDTO;
    }
}
