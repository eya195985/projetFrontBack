package com.example.springboot.first.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "statut_annonce")
public class StatutAnnonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut_annonce")
    private Long idStatutAnnonce;

    @Column(name = "statut", nullable = false) // Utilisez "statut" au lieu de "libellé"
    private String statut;

    // Getters et Setters
    public Long getIdStatutAnnonce() {
        return idStatutAnnonce;
    }

    public void setIdStatutAnnonce(Long idStatutAnnonce) {
        this.idStatutAnnonce = idStatutAnnonce;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}