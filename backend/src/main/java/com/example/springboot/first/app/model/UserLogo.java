package com.example.springboot.first.app.model;

import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Column;
import java.nio.ByteBuffer;
import java.util.UUID;

@Table("user_logo") // Nom de la table dans Cassandra
public class UserLogo {

    @PrimaryKey
    @Column("id_logo") // Nom de la colonne dans Cassandra
    private UUID idLogo; // ID du logo

    @Column("logo")
    private ByteBuffer logo; // Utilisation de ByteBuffer pour le logo

    // Constructeur
    public UserLogo(UUID idLogo, ByteBuffer logo) {
        this.idLogo = idLogo;
        this.logo = logo;
    }

    // Getters et setters
    public UUID getIdLogo() {
        return idLogo;
    }

    public void setIdLogo(UUID idLogo) {
        this.idLogo = idLogo;
    }

    public ByteBuffer getLogo() {
        return logo;
    }

    public void setLogo(ByteBuffer logo) {
        this.logo = logo;
    }
}