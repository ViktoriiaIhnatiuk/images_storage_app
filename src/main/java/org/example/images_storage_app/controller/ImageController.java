package org.example.images_storage_app.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {
    @GetMapping
    public String getImages() {
        return "all images will be shown here";
    }

    @PostMapping(consumes = "multipart/form-data")
    public String uploadImage(@RequestPart("file") MultipartFile file) {
        return "Uploaded: " + file.getOriginalFilename();
    }
}