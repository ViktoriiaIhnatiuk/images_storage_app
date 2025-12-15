package org.example.images_storage_app.repository;

import org.example.images_storage_app.model.ImageLabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageLabelRepository extends JpaRepository<ImageLabelEntity, Long> {
}
