package com.example.springboot.first.app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "service_transport")
public class ServiceTransport implements Serializable {

    @EmbeddedId
    private ServiceTransportId id;

    @ManyToOne
    @MapsId("idService") // Lie cet attribut à la colonne id_service dans ServiceTransportId
    @JoinColumn(name = "id_service")
    @JsonBackReference
    private Services service;

    @ManyToOne
    @MapsId("idTransport") // Lie cet attribut à la colonne id_transport dans ServiceTransportId
    @JoinColumn(name = "id_transport")
    @JsonManagedReference
    private Transport transport;

    // Getters et Setters
    public ServiceTransportId getId() {
        return id;
    }

    public void setId(ServiceTransportId id) {
        this.id = id;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceTransport that = (ServiceTransport) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}