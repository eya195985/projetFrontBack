package com.example.springboot.first.app.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "pays_destination")
public class Pays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pays_destination")
    private Long idPaysDestination;

    @Column(name = "nom_pays", nullable = false)
    private String nomPays;



    // Getters & Setters
    public Long getIdPaysDestination() {
        return idPaysDestination;
    }

    public void setIdPaysDestination(Long idPaysDestination) {
        this.idPaysDestination = idPaysDestination;
    }

    public String getNomPays() {
        return nomPays;
    }

    public void setNomPays(String nomPays) {
        this.nomPays = nomPays;
    }


}
