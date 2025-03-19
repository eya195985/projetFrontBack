package com.example.springboot.first.app.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Service_Supplementaire")
public class ServiceSupplementaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_service_supplémentaire")
    private Long id;

    @Column(name = "nom_service", nullable = false)
    private String nomService;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Relation ManyToMany avec la table de jointure
    @OneToMany(mappedBy = "serviceSupplementaire", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference // Permet la sérialisation de cette propriété
    private Set<Supplementaires> supplementaires;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
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