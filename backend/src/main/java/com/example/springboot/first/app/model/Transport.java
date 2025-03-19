package com.example.springboot.first.app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.Set;

@Entity
@Table(name = "transport")
public class Transport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Génération automatique de l'ID
    private Long idTransport;

    private String compagnie;

    // Relation Many-to-One avec TypeTransport
    @ManyToOne
    @JoinColumn(name = "id_type_transport", nullable = false) // Colonne de jointure dans la table transport
    @JsonManagedReference // Pour éviter les références circulaires lors de la sérialisation JSON
    private TypeTransport typeTransport;

    // Relation One-to-Many avec ServiceTransport (table de jointure)
    @OneToMany(mappedBy = "transport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<ServiceTransport> serviceTransports;

    // Getters et setters
    public Long getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(Long idTransport) {
        this.idTransport = idTransport;
    }

    public String getCompagnie() {
        return compagnie;
    }

    public void setCompagnie(String compagnie) {
        this.compagnie = compagnie;
    }

    public TypeTransport getTypeTransport() {
        return typeTransport;
    }

    public void setTypeTransport(TypeTransport typeTransport) {
        this.typeTransport = typeTransport;
    }

    public Set<ServiceTransport> getServiceTransports() {
        return serviceTransports;
    }

    public void setServiceTransports(Set<ServiceTransport> serviceTransports) {
        this.serviceTransports = serviceTransports;
    }
}