package org.example.images_storage_app.model;

import jakarta.persistence.*;

@Entity
public class ImageLabelEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   private String label;
   private float confidenceValue;
   @ManyToOne
   private ImageEntity image;

    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getConfidenceValue() {
        return confidenceValue;
    }

    public void setConfidenceValue(float confidenceValue) {
        this.confidenceValue = confidenceValue;
    }

    public ImageEntity getImage() {
        return image;
    }

    public void setImage(ImageEntity image) {
        this.image = image;
    }
}
