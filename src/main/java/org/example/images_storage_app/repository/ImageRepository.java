package org.example.images_storage_app.repository;

import org.example.images_storage_app.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    @Query("""
        select distinct l.image
        from ImageLabelEntity l
        where lower(l.label) = lower(:label)
    """)
    List<ImageEntity> findByLabel(@Param("label") String label);

}
