package org.example.images_storage_app.service;

import lombok.RequiredArgsConstructor;
import org.example.images_storage_app.model.ImageEntity;
import org.example.images_storage_app.model.ImageLabelEntity;
import org.example.images_storage_app.model.ImageStatus;
import org.example.images_storage_app.repository.ImageLabelRepository;
import org.example.images_storage_app.repository.ImageRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RekognitionAsyncService {

    private final RekognitionService rekognitionService;
    private final ImageRepository imageRepository;
    private final ImageLabelRepository imageLabelRepository;

    @Async("rekognitionExecutor")
    @Transactional
    public void processAsync(Long imageId, String bucketName, String objectKey) {

        ImageEntity image =
                imageRepository.findById(imageId).orElseThrow();

        try {
            List<ImageLabelEntity> labels = rekognitionService.analyzeFromS3(bucketName, objectKey, image);
            imageLabelRepository.saveAll(labels);
            image.setStatus(ImageStatus.READY);

        } catch (Exception e) {
            image.setStatus(ImageStatus.FAILED);
        }
    }
}
