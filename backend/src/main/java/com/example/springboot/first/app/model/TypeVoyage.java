package com.example.springboot.first.app.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "Type_Voyage")
public class TypeVoyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Correction ici : IDENTITY au lieu de IDENTITY
    @Column(name = "id_type_voyage")
    private Long idTypeVoyage;

    @Column(name = "nom_type_voyage", nullable = false)
    private String nomTypeVoyage;

    // Getters et Setters
    public Long getIdTypeVoyage() {
        return idTypeVoyage;
    }

    public void setIdTypeVoyage(Long idTypeVoyage) {
        this.idTypeVoyage = idTypeVoyage;
    }

    public String getNomTypeVoyage() {
        return nomTypeVoyage;
    }

    public void setNomTypeVoyage(String nomTypeVoyage) {
        this.nomTypeVoyage = nomTypeVoyage;
    }

    // Méthode toString pour le débogage
    @Override
    public String toString() {
        return "TypeVoyage{" +
                "idTypeVoyage=" + idTypeVoyage +
                ", nomTypeVoyage='" + nomTypeVoyage + '\'' +
                '}';
    }

    // Méthode equals et hashCode pour la comparaison d'objets
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeVoyage that = (TypeVoyage) o;
        return Objects.equals(idTypeVoyage, that.idTypeVoyage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTypeVoyage);
    }
}