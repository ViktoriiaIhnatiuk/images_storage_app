package org.example.images_storage_app.service;

import lombok.RequiredArgsConstructor;
import org.example.images_storage_app.exception.ImageAnalysisException;
import org.example.images_storage_app.exception.UnsupportedImageFormatException;
import org.example.images_storage_app.model.ImageEntity;
import org.example.images_storage_app.model.ImageLabelEntity;
import org.example.images_storage_app.repository.ImageLabelRepository;
import org.example.images_storage_app.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Service s3Service;
    private final RekognitionService rekognitionService;
    private final ImageRepository imageRepository;
    private final ImageLabelRepository imageLabelRepository;
    private final ImageValidationService validationService;

    private final String BUCKET_NAME = "images-storage-app-bucket-west-region";

    @Transactional
    public String upload(MultipartFile file) {
        validationService.validate(file);

        String fileName = file.getOriginalFilename();
        byte[] bytes;

        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            throw new ImageAnalysisException("Cannot read file bytes");
        }

        try {
            s3Service.upload(BUCKET_NAME, fileName, bytes);
            String url = "https://" + BUCKET_NAME + ".s3.eu-west-1.amazonaws.com/" + fileName;
            ImageEntity image = new ImageEntity();
            image.setFileName(fileName);
            image.setUrl(url);
            imageRepository.save(image);

            List<ImageLabelEntity> labels =
                    rekognitionService.analyze(bytes, image);

            imageLabelRepository.saveAll(labels);

        } catch (Exception e) {
            s3Service.delete(BUCKET_NAME, fileName);
            System.out.println("Failed to upload image " + fileName);
            throw new UnsupportedImageFormatException("Failed to upload image file, please, upload correct image file");
        }
        return "Uploaded successfully";
    }

    public List<ImageEntity> getImages() {
        return imageRepository.findAll();
    }

    public List<ImageEntity> getImagesByLabel(String label) {
        return imageRepository.findByLabel(label);
    }
}
