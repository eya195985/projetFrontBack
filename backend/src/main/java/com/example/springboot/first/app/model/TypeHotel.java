package com.example.springboot.first.app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "type_hotel")
public class TypeHotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémentation de l'ID
    @Column(name = "id_type_hotel") // Assure que le mapping est correct
    private Integer idTypeHotel;

    @Column(name = "categorie", nullable = false)
    private String categorie;

    @Column(name = "frais_supplementaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal fraisSupplementaire;

    // Getters et setters
    public Integer getIdTypeHotel() {
        return idTypeHotel;
    }

    public void setIdTypeHotel(Integer idTypeHotel) {
        this.idTypeHotel = idTypeHotel;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public BigDecimal getFraisSupplementaire() {
        return fraisSupplementaire;
    }

    public void setFraisSupplementaire(BigDecimal fraisSupplementaire) {
        this.fraisSupplementaire = fraisSupplementaire;
    }
}
