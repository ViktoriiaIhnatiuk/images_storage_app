package org.example.images_storage_app.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "images")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String url;
    @Enumerated(EnumType.STRING)
    private ImageStatus status;
    @OneToMany(mappedBy = "image", fetch = FetchType.LAZY)
    private List<ImageLabelEntity> imageLabels;


    public List<ImageLabelEntity> getImageLabels() {
        return imageLabels;
    }

    public void setImageLabels(List<ImageLabelEntity> imageLabels) {
        this.imageLabels = imageLabels;
    }

    public ImageStatus getStatus() {
        return status;
    }

    public void setStatus(ImageStatus status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
