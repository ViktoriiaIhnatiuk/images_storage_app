package org.example.images_storage_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;

    public void upload(String bucketName, String key, MultipartFile file) {

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(request, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    public void delete(String bucketName, String key) {
        s3Client.deleteObject(builder ->
                builder.bucket(bucketName).key(key)
        );
    }
}
