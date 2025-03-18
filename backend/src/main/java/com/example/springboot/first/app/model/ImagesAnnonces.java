package com.example.springboot.first.app.model;

import jakarta.persistence.*;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.UUID;

@Table("images_annonces") // Nom de la table dans Cassandra
public class ImagesAnnonces {

    @PrimaryKey
    @Column(name = "id_image") // Nom de la colonne dans Cassandra
    private UUID idImage; // ID de l'image

    @Column(name = "image_data")
    private ByteBuffer imageData; // Données binaires de l'image (ByteBuffer)

    // Constructeur
    public ImagesAnnonces(UUID idImage, ByteBuffer imageData) {
        this.idImage = idImage;
        this.imageData = imageData;
    }

    // Getters et Setters
    public UUID getIdImage() {
        return idImage;
    }

    public void setIdImage(UUID idImage) {
        this.idImage = idImage;
    }

    public ByteBuffer getImageData() {
        return imageData;
    }

    public void setImageData(ByteBuffer imageData) {
        this.imageData = imageData;
    }

    // Equals et HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImagesAnnonces that = (ImagesAnnonces) o;
        return Objects.equals(idImage, that.idImage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idImage);
    }

    // ToString
    @Override
    public String toString() {
        return "ImagesAnnonces{" +
                "idImage=" + idImage +
                '}';
    }
}