package org.example.images_storage_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Client s3Client;

    public void upload(String bucketName, String key, byte[] data) {
        PutObjectRequest request = PutObjectRequest.builder().bucket(bucketName).key(key).build();
        s3Client.putObject(request, RequestBody.fromBytes(data));
    }
}
