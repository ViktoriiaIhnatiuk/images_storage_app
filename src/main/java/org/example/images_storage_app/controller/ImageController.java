package org.example.images_storage_app.controller;

import org.example.images_storage_app.service.S3Service;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {
    private final S3Service s3Service;
    private final String BUCKET_NAME = "images-storage-app-bucket";

    public ImageController(S3Service s3Service) {
        this.s3Service = s3Service;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileName + "has been uploaded: ";
    }
}