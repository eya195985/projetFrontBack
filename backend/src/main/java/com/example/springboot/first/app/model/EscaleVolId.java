package com.example.springboot.first.app.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


import jakarta.persistence.*;


@Embeddable
public class EscaleVolId implements Serializable {

    @Column(name = "id_vol")
    private Long idVol;

    @Column(name = "id_pays_destination")
    private Long idPaysDestination;

    // Constructeurs
    public EscaleVolId() {}

    public EscaleVolId(Long idVol, Long idPaysDestination) {
        this.idVol = idVol;
        this.idPaysDestination = idPaysDestination;
    }

    // Getters et setters
    public Long getIdVol() {
        return idVol;
    }

    public void setIdVol(Long idVol) {
        this.idVol = idVol;
    }

    public Long getIdPaysDestination() {
        return idPaysDestination;
    }

    public void setIdPaysDestination(Long idPaysDestination) {
        this.idPaysDestination = idPaysDestination;
    }

    // Equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EscaleVolId that = (EscaleVolId) o;
        return Objects.equals(idVol, that.idVol) && Objects.equals(idPaysDestination, that.idPaysDestination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVol, idPaysDestination);
    }

    // ToString
    @Override
    public String toString() {
        return "EscaleVolId{" +
                "idVol=" + idVol +
                ", idPaysDestination=" + idPaysDestination +
                '}';
    }
}
