package com.example.springboot.first.app.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.Column;

import java.util.UUID;
import java.time.Instant;

@Table("logo_images")
public class LogoImage {

    @PrimaryKey
    private UUID logoUid;

    @Column("image_blob")
    private byte[] imageBlob; // Utilisez byte[] pour stocker des données binaires dans Cassandra

    @Column("uploaded_at")
    private Instant uploadedAt;

    // Constructeur
    @PersistenceConstructor
    public LogoImage(UUID logoUid, byte[] imageBlob, Instant uploadedAt) {
        this.logoUid = logoUid;
        this.imageBlob = imageBlob;
        this.uploadedAt = uploadedAt;
    }

    // Getters and Setters
    public UUID getLogoUid() {
        return logoUid;
    }

    public void setLogoUid(UUID logoUid) {
        this.logoUid = logoUid;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }

    public void setImageBlob(byte[] imageBlob) {
        this.imageBlob = imageBlob;
    }

    public Instant getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Instant uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
}
