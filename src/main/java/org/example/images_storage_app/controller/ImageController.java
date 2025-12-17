package org.example.images_storage_app.controller;

import org.example.images_storage_app.dto.response.ImageEntityResponseDTO;
import org.example.images_storage_app.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;


    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping
    public List<ImageEntityResponseDTO> getImages() {
        return imageService.getImages();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadImage(@RequestPart("file") MultipartFile file) {
        imageService.upload(file);
    }

    @GetMapping("/search")
    public List<ImageEntityResponseDTO> searchByLabel(@RequestParam String label) {
        return imageService.getImagesByLabel(label);
    }
}