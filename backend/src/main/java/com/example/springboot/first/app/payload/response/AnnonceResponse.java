package com.example.springboot.first.app.payload.response;

import com.example.springboot.first.app.model.StatutAnnonce;
import com.example.springboot.first.app.model.Voyage;

public class AnnonceResponse {
    private Long idAnnonce;
    private Voyage voyage;
    private StatutAnnonce statut;

    public Long getIdAnnonce() {
        return idAnnonce;
    }

    public void setIdAnnonce(Long idAnnonce) {
        this.idAnnonce = idAnnonce;
    }

    public Voyage getVoyage() {
        return voyage;
    }

    public void setVoyage(Voyage voyage) {
        this.voyage = voyage;
    }

    public StatutAnnonce getStatut() {
        return statut;
    }

    public void setStatut(StatutAnnonce statut) {
        this.statut = statut;
    }
}
