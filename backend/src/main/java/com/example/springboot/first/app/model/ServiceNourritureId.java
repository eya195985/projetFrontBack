package com.example.springboot.first.app.model;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ServiceNourritureId implements Serializable {

    @Column(name = "id_service")
    private Long idService;

    @Column(name = "id_nourriture")
    private Long idNourriture;

    // Constructeurs, Getters et Setters
    public ServiceNourritureId() {}

    public ServiceNourritureId(Long idService, Long idNourriture) {
        this.idService = idService;
        this.idNourriture = idNourriture;
    }

    public Long getIdService() {
        return idService;
    }

    public void setIdService(Long idService) {
        this.idService = idService;
    }

    public Long getIdNourriture() {
        return idNourriture;
    }

    public void setIdNourriture(Long idNourriture) {
        this.idNourriture = idNourriture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceNourritureId that = (ServiceNourritureId) o;
        return Objects.equals(idService, that.idService) && Objects.equals(idNourriture, that.idNourriture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idService, idNourriture);
    }
}
