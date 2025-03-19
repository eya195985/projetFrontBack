package com.example.springboot.first.app.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Service_Nourriture")
public class ServiceNourriture implements Serializable {

    @EmbeddedId
    private ServiceNourritureId id;

    @ManyToOne
    @MapsId("idService")
    @JoinColumn(name = "id_service")
    @JsonBackReference
    private Services services;

    @ManyToOne
    @MapsId("idNourriture")
    @JoinColumn(name = "id_nourriture")
    @JsonManagedReference
    private Nourriture nourriture;

    @ManyToOne
    @MapsId("idService") // Lie cet attribut à la colonne id_service dans ServiceTransportId
    @JoinColumn(name = "id_service")
    @JsonBackReference
    private Services service;

    // Getters et Setters
    public ServiceNourritureId getId() {
        return id;
    }

    public void setId(ServiceNourritureId id) {
        this.id = id;
    }

    public Services getService() {
        return services;
    }

    public void setService(Services services) {
        this.services = services;
    }

    public Nourriture getNourriture() {
        return nourriture;
    }

    public void setNourriture(Nourriture nourriture) {
        this.nourriture = nourriture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceNourriture that = (ServiceNourriture) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
