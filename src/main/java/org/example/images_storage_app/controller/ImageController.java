package org.example.images_storage_app.controller;

import org.example.images_storage_app.dto.response.ImageEntityResponseDTO;
import org.example.images_storage_app.mapper.ImageEntityMapper;
import org.example.images_storage_app.repository.ImageRepository;
import org.example.images_storage_app.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/images")
public class ImageController {
    private final ImageRepository imageRepository;
    private final ImageEntityMapper imageEntityMapper;
    private final ImageService imageService;


    public ImageController(ImageRepository imageRepository, ImageEntityMapper imageEntityMapper, ImageService imageService) {
        this.imageRepository = imageRepository;
        this.imageEntityMapper = imageEntityMapper;
        this.imageService = imageService;
    }

    @GetMapping
    public List<ImageEntityResponseDTO> getImages() {
        return imageService.getImages().stream().map(imageEntityMapper :: mapToDTO).collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadImage(@RequestPart("file") MultipartFile file) {
        return imageService.upload(file);
    }

    @GetMapping("/search")
    public List<ImageEntityResponseDTO> searchByLabel(
            @RequestParam String label
    ) {
        return imageService.getImagesByLabel(label).stream().map(imageEntityMapper :: mapToDTO ).toList();
    }
}