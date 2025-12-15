package org.example.images_storage_app.service;

import org.example.images_storage_app.model.ImageEntity;
import org.example.images_storage_app.model.ImageLabelEntity;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsRequest;
import software.amazon.awssdk.services.rekognition.model.DetectLabelsResponse;
import software.amazon.awssdk.services.rekognition.model.Image;

import java.io.IOException;
import java.util.List;

@Service
public class RekognitionService {
    private final RekognitionClient rekognitionClient =  RekognitionClient.builder().build();

    public List<ImageLabelEntity> analyze(byte[] data, ImageEntity imageEntity) throws IOException {
        Image awsImage = Image.builder().bytes(SdkBytes.fromByteArray(data)).build();

        DetectLabelsRequest labelsRequest = DetectLabelsRequest.builder().image(awsImage).maxLabels(10)
                .minConfidence(50f).build();

        DetectLabelsResponse response = rekognitionClient.detectLabels(labelsRequest);

        return response.labels().stream().map(label -> {
            ImageLabelEntity imageLabelEntity = new ImageLabelEntity();
            imageLabelEntity.setLabel(label.name());
            imageLabelEntity.setConfidenceValue(label.confidence());
            imageLabelEntity.setImage(imageEntity);
            return imageLabelEntity;
        }).toList();
    }
}
