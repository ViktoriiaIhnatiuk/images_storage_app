package org.example.images_storage_app.controller;

import org.example.images_storage_app.model.ImageEntity;
import org.example.images_storage_app.model.ImageLabelEntity;
import org.example.images_storage_app.repository.ImageLabelRepository;
import org.example.images_storage_app.repository.ImageRepository;
import org.example.images_storage_app.service.RekognitionService;
import org.example.images_storage_app.service.S3Service;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final S3Service s3Service;
    private final String BUCKET_NAME = "images-storage-app-bucket-west-region";
    private final ImageRepository imageRepository;
    private final RekognitionService rekognitionService;
    private final ImageLabelRepository imageLabelRepository;

    public ImageController(S3Service s3Service, ImageRepository imageRepository, RekognitionService rekognitionService, ImageLabelRepository imageLabelRepository) {
        this.s3Service = s3Service;
        this.imageRepository = imageRepository;
        this.rekognitionService = rekognitionService;
        this.imageLabelRepository = imageLabelRepository;
    }

    @GetMapping
    public String getImages() {
        return "all images will be shown here";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(@RequestPart("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            s3Service.upload(BUCKET_NAME, fileName, file.getBytes());
            ImageEntity imageEntity = new ImageEntity();
            imageEntity.setFileName(fileName);
            imageRepository.save(imageEntity);
            List<ImageLabelEntity> labels = rekognitionService.analyze(file.getBytes(), imageEntity);
            imageLabelRepository.saveAll(labels);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName + " has been uploaded: ";
    }
}