package org.example.images_storage_app.service;

import lombok.RequiredArgsConstructor;
import org.example.images_storage_app.model.ImageEntity;
import org.example.images_storage_app.model.ImageStatus;
import org.example.images_storage_app.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Service s3Service;
    private final RekognitionAsyncService rekognitionAsyncService;
    private final ImageRepository imageRepository;
    private final ImageValidationService validationService;

    private final String BUCKET_NAME = "images-storage-app-bucket-west-region";

    public void upload(MultipartFile file) {
        validationService.validate(file);

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        s3Service.upload(BUCKET_NAME, fileName, file);

        ImageEntity image = new ImageEntity();
        image.setFileName(fileName);
        image.setStatus(ImageStatus.PROCESSING);
        image = imageRepository.save(image);

        try {
            rekognitionAsyncService.processAsync(image.getId(), BUCKET_NAME, fileName);
        } catch (Exception e) {
            System.out.println("Failed to start async rekognition");
        }
    }

    public List<ImageEntity> getImages() {
        return imageRepository.findAll();
    }

    public List<ImageEntity> getImagesByLabel(String label) {
        return imageRepository.findByLabel(label);
    }
}
