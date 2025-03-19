package com.example.springboot.first.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "itinéraire")
public class Itineraire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_itinéraire")
    private Long id;

    @Column(name = "activités", columnDefinition = "TEXT")
    private String activites;

    @Column(name = "destination_région_jour", length = 255)
    private String destinationRegionJour;

    @ManyToOne
    @JoinColumn(name = "id_voyage", nullable = false)
    @JsonBackReference
    private Voyage voyage;

    @Transient // Cette annotation indique que le champ n'est pas persisté en base de données
    private List<Services> services; // Liste des services associés

    // Getters et setters
    public List<Services> getServices() {
        return services;
    }

    public void setServices(List<Services> services) {
        this.services = services;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivites() {
        return activites;
    }

    public void setActivites(String activites) {
        this.activites = activites;
    }

    public String getDestinationRegionJour() {
        return destinationRegionJour;
    }

    public void setDestinationRegionJour(String destinationRegionJour) {
        this.destinationRegionJour = destinationRegionJour;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }
}