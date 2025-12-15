package org.example.images_storage_app.repository;

import org.example.images_storage_app.model.ImageEntity;
import org.example.images_storage_app.model.ImageLabelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageLabelRepository extends JpaRepository<ImageLabelEntity, Long> {
    List<ImageLabelEntity> getImageLabelEntitiesByImage(ImageEntity imageEntity);
}
