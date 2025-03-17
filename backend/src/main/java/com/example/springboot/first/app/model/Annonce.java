package com.example.springboot.first.app.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "annonce")
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_annonce")
    private Long idAnnonce;

    @ManyToOne
    @JoinColumn(name = "id_concessionnaire", nullable = false) // Ajoutez cette relation
    private Concessionnaire concessionnaire;

    @ManyToOne
    @JoinColumn(name = "id_voyage", nullable = false)
    private Voyage voyage;

    @ManyToOne
    @JoinColumn(name = "id_statut_annonce", nullable = false)
    private StatutAnnonce statutAnnonce;

    // Getters et Setters
    public Long getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(Long idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public Concessionnaire getConcessionnaire() {
        return concessionnaire;
    }

    public void setConcessionnaire(Concessionnaire concessionnaire) {
        this.concessionnaire = concessionnaire;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public StatutAnnonce getStatutAnnonce() {
        return statutAnnonce;
    }

    public void setStatutAnnonce(StatutAnnonce statutAnnonce) {
        this.statutAnnonce = statutAnnonce;
    }

    // Equals et HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Annonce annonce = (Annonce) o;
        return Objects.equals(idAnnonce, annonce.idAnnonce);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAnnonce);
    }

    // ToString
    @Override
    public String toString() {
        return "Annonce{" +
                "idAnnonce=" + idAnnonce +
                ", concessionnaire=" + concessionnaire +
                ", voyage=" + voyage +
                ", statutAnnonce=" + statutAnnonce +
                '}';
    }
}