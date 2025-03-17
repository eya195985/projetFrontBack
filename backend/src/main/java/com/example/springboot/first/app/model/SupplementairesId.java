package com.example.springboot.first.app.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SupplementairesId implements Serializable {

    private Long serviceSupplementaireId;
    private Long serviceId;

    // Getters et Setters
    public Long getServiceSupplementaireId() {
        return serviceSupplementaireId;
    }

    public void setServiceSupplementaireId(Long serviceSupplementaireId) {
        this.serviceSupplementaireId = serviceSupplementaireId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    // Equals et HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplementairesId that = (SupplementairesId) o;
        return Objects.equals(serviceSupplementaireId, that.serviceSupplementaireId) &&
                Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceSupplementaireId, serviceId);
    }
}