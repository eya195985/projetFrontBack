package com.example.springboot.first.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ServiceTransportId implements Serializable {

    @Column(name = "id_service")
    private Long idService;

    @Column(name = "id_transport")
    private Long idTransport;

    // Constructeurs, Getters et Setters
    public ServiceTransportId() {}

    public ServiceTransportId(Long idService, Long idTransport) {
        this.idService = idService;
        this.idTransport = idTransport;
    }

    public Long getIdService() {
        return idService;
    }

    public void setIdService(Long idService) {
        this.idService = idService;
    }

    public Long getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(Long idTransport) {
        this.idTransport = idTransport;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceTransportId that = (ServiceTransportId) o;
        return Objects.equals(idService, that.idService) && Objects.equals(idTransport, that.idTransport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idService, idTransport);
    }
}