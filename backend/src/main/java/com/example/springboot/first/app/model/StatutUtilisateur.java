package com.example.springboot.first.app.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "statut_utilisateur")
public class StatutUtilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statut")
    private Long idStatut;

    @Column(name = "statut", nullable = false)
    private String statut; // "actif", "en attente", "refusé"

    public String getStatut() {
        return statut;
    }
}