package com.example.springboot.first.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Services")
public class Services {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_hôtel")
    private Hotel hotel;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Relation ManyToMany avec la table de jointure
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Supplementaires> supplementaires;

    // Relation Many-to-Many avec Transport via la table de jointure ServiceTransport
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference // Ignore la sérialisation de cette référence pour éviter les références circulaires
    private Set<ServiceTransport> serviceTransports;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Supplementaires> getSupplementaires() {
        return supplementaires;
    }

    public void setSupplementaires(Set<Supplementaires> supplementaires) {
        this.supplementaires = supplementaires;
    }
}