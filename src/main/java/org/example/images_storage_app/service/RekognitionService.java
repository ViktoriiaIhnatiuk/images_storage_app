package org.example.images_storage_app.service;

import lombok.RequiredArgsConstructor;
import org.example.images_storage_app.model.ImageEntity;
import org.example.images_storage_app.model.ImageLabelEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.Image;
import software.amazon.awssdk.services.rekognition.model.S3Object;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RekognitionService {

    private final RekognitionClient rekognitionClient;

    public List<ImageLabelEntity> analyzeFromS3(
            String bucketName,
            String objectKey,
            ImageEntity imageEntity
    ) {

        Image awsImage = Image.builder()
                .s3Object(S3Object.builder()
                        .bucket(bucketName)
                        .name(objectKey)
                        .build())
                .build();

        DetectLabelsRequest request = DetectLabelsRequest.builder()
                .image(awsImage)
                .maxLabels(10)
                .minConfidence(50f)
                .build();

        DetectLabelsResponse response =
                rekognitionClient.detectLabels(request);

        return response.labels().stream()
                .map(label -> {
                    ImageLabelEntity entity = new ImageLabelEntity();
                    entity.setLabel(label.name());
                    entity.setConfidenceValue(label.confidence());
                    entity.setImage(imageEntity);
                    return entity;
                })
                .toList();
    }
}
