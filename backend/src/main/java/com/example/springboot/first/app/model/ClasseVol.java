package com.example.springboot.first.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "classe_vol") // Nom de la table dans la base de données
public class ClasseVol {

    @Id // Indique que ce champ est la clé primaire
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incrémenté par la base de données
    @Column(name = "id_classe_vol") // Nom de la colonne dans la base de données
    private int idClasseVoyage;

    @Column(name = "nom_classe", nullable = false) // Nom de la colonne et contrainte NOT NULL
    private String nomClasse;

    @Column(name = "frais_supplementaire", nullable = false) // Nom de la colonne et contrainte NOT NULL
    private double fraisSupplementaire;

    // Constructeurs
    public ClasseVol() {}

    public ClasseVol(String nomClasse, double fraisSupplementaire) {
        this.nomClasse = nomClasse;
        this.fraisSupplementaire = fraisSupplementaire;
    }

    // Getters et Setters
    public int getIdClasseVoyage() {
        return idClasseVoyage;
    }

    public void setIdClasseVoyage(int idClasseVoyage) {
        this.idClasseVoyage = idClasseVoyage;
    }

    public String getNomClasse() {
        return nomClasse;
    }

    public void setNomClasse(String nomClasse) {
        this.nomClasse = nomClasse;
    }

    public double getFraisSupplementaire() {
        return fraisSupplementaire;
    }

    public void setFraisSupplementaire(double fraisSupplementaire) {
        this.fraisSupplementaire = fraisSupplementaire;
    }

    // Méthode toString pour le débogage
    @Override
    public String toString() {
        return "ClasseVol{" +
                "idClasseVoyage=" + idClasseVoyage +
                ", nomClasse='" + nomClasse + '\'' +
                ", fraisSupplementaire=" + fraisSupplementaire +
                '}';
    }
}