package com.example.springboot.first.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "Supplementaires")
public class Supplementaires {

    @EmbeddedId
    private SupplementairesId id;

    @ManyToOne
    @MapsId("serviceSupplementaireId")
    @JoinColumn(name = "id_service_supplémentaire")
    @JsonManagedReference
    private ServiceSupplementaire serviceSupplementaire;

    @ManyToOne
    @MapsId("serviceId")
    @JoinColumn(name = "id_service")
    @JsonBackReference
    private Services service;


    // Getters et Setters
    public SupplementairesId getId() {
        return id;
    }

    public void setId(SupplementairesId id) {
        this.id = id;
    }

    public ServiceSupplementaire getServiceSupplementaire() {
        return serviceSupplementaire;
    }

    public void setServiceSupplementaire(ServiceSupplementaire serviceSupplementaire) {
        this.serviceSupplementaire = serviceSupplementaire;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }


}