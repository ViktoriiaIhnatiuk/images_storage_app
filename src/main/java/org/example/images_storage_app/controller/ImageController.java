package org.example.images_storage_app.controller;

import org.example.images_storage_app.dto.request.ImageLabelEntityRequestDTO;
import org.example.images_storage_app.dto.response.ImageEntityResponseDTO;
import org.example.images_storage_app.dto.response.ImageLabelEntityResponseDTO;
import org.example.images_storage_app.mapper.ImageEntityMapper;
import org.example.images_storage_app.mapper.ImageLabelEntityMapper;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final S3Service s3Service;
    private final String BUCKET_NAME = "images-storage-app-bucket-west-region";
    private final ImageRepository imageRepository;
    private final RekognitionService rekognitionService;
    private final ImageLabelRepository imageLabelRepository;
    private final ImageEntityMapper imageEntityMapper;
    private final ImageLabelEntityMapper imageLabelEntityMapper;


    public ImageController(S3Service s3Service, ImageRepository imageRepository, RekognitionService rekognitionService, ImageLabelRepository imageLabelRepository, ImageEntityMapper imageEntityMapper, ImageLabelEntityMapper imageLabelEntityMapper) {
        this.s3Service = s3Service;
        this.imageRepository = imageRepository;
        this.rekognitionService = rekognitionService;
        this.imageLabelRepository = imageLabelRepository;
        this.imageEntityMapper = imageEntityMapper;
        this.imageLabelEntityMapper = imageLabelEntityMapper;
    }

    @GetMapping
    public List<ImageEntityResponseDTO> getImages() {
        List<ImageEntityResponseDTO> imageEntities = imageRepository.findAll().stream().map(imageEntityMapper :: mapToDTO ).collect(Collectors.toList());
        return imageEntities;
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