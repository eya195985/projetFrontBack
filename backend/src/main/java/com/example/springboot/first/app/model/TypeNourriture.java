package com.example.springboot.first.app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "type_nourriture")
public class TypeNourriture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ajout de l'auto-génération
    @Column(name = "id_type_nourriture") // S'assurer que le nom correspond à la DB
    private Integer idTypeNourriture;

    @Column(name = "nom_type", nullable = false) // Ajout de contraintes pour éviter les erreurs
    private String nomType;

    @Column(name = "frais_nourriture", nullable = false, precision = 10, scale = 2)
    private BigDecimal fraisNourriture;

    // Getters et setters
    public Integer getIdTypeNourriture() {
        return idTypeNourriture;
    }

    public void setIdTypeNourriture(Integer idTypeNourriture) {
        this.idTypeNourriture = idTypeNourriture;
    }

    public String getNomType() {
        return nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    public BigDecimal getFraisNourriture() {
        return fraisNourriture;
    }

    public void setFraisNourriture(BigDecimal fraisNourriture) {
        this.fraisNourriture = fraisNourriture;
    }
}
