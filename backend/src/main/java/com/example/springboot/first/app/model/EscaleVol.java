package com.example.springboot.first.app.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "escale_vol")
public class EscaleVol {

    @EmbeddedId
    private EscaleVolId id;

    @ManyToOne
    @MapsId("idVol") // Correspond à la colonne id_vol dans la table Escale_Vol
    @JoinColumn(name = "id_vol", nullable = false)
    private Vol vol;

    @ManyToOne
    @MapsId("idPaysDestination") // Correspond à la colonne id_pays_destination dans la table Escale_Vol
    @JoinColumn(name = "id_pays_destination", nullable = false)
    private Pays paysDestination;

    // Constructeurs
    public EscaleVol() {}

    public EscaleVol(Vol vol, Pays paysDestination) {
        this.id = new EscaleVolId(vol.getIdVol(), paysDestination.getIdPaysDestination());
        this.vol = vol;
        this.paysDestination = paysDestination;
    }

    // Getters et setters
    public EscaleVolId getId() {
        return id;
    }

    public void setId(EscaleVolId id) {
        this.id = id;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public Pays getPaysDestination() {
        return paysDestination;
    }

    public void setPaysDestination(Pays paysDestination) {
        this.paysDestination = paysDestination;
    }

    // Equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EscaleVol escaleVol = (EscaleVol) o;
        return Objects.equals(id, escaleVol.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // ToString
    @Override
    public String toString() {
        return "EscaleVol{" +
                "id=" + id +
                ", vol=" + vol +
                ", paysDestination=" + paysDestination +
                '}';
    }
}