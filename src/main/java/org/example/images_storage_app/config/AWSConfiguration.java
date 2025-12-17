package org.example.images_storage_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class AWSConfiguration {

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.EU_WEST_1)
                .build();
    }

    @Bean
    public RekognitionClient rekognitionClient() {
        return RekognitionClient.builder()
                .region(Region.EU_WEST_1)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }
}
