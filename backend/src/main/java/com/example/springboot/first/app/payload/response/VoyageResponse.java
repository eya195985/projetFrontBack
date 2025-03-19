package com.example.springboot.first.app.payload.response;
import com.example.springboot.first.app.model.*;

import java.util.List;

public class  VoyageResponse {

    private Long idVoyage;
    private String titreVoyage;
    private String description;
    private Double prix;
    private Integer duree;
    private Long idTypeVoyage;
    private TypeVoyage typeVoyage;
    private CircuitEnum circuit;
    private Integer tailleGroupe;
    private String trancheAge;
    private GuideEnum guide;
    private String langueGuide;
    private Vol vol;
    private Integer ageMin;
    private Integer ageMax;
    private List<Itineraire> itineraires;

    // Getters and Setters
    public Long getIdVoyage() {
        return idVoyage;
    }

    public void setIdVoyage(Long idVoyage) {
        this.idVoyage = idVoyage;
    }

    public String getTitreVoyage() {
        return titreVoyage;
    }

    public void setTitreVoyage(String titreVoyage) {
        this.titreVoyage = titreVoyage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Long getIdTypeVoyage() {
        return idTypeVoyage;
    }

    public void setIdTypeVoyage(Long idTypeVoyage) {
        this.idTypeVoyage = idTypeVoyage;
    }

    public TypeVoyage getTypeVoyage() {
        return typeVoyage;
    }

    public void setTypeVoyage(TypeVoyage typeVoyage) {
        this.typeVoyage = typeVoyage;
    }

    public CircuitEnum getCircuit() {
        return circuit;
    }

    public void setCircuit(CircuitEnum circuit) {
        this.circuit = circuit;
    }

    public Integer getTailleGroupe() {
        return tailleGroupe;
    }

    public void setTailleGroupe(Integer tailleGroupe) {
        this.tailleGroupe = tailleGroupe;
    }

    public String getTrancheAge() {
        return trancheAge;
    }

    public void setTrancheAge(String trancheAge) {
        this.trancheAge = trancheAge;
    }

    public GuideEnum getGuide() {
        return guide;
    }

    public void setGuide(GuideEnum guide) {
        this.guide = guide;
    }

    public String getLangueGuide() {
        return langueGuide;
    }

    public void setLangueGuide(String langueGuide) {
        this.langueGuide = langueGuide;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }

    public Integer getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(Integer ageMax) {
        this.ageMax = ageMax;
    }

    public List<Itineraire> getItineraires() {
        return itineraires;
    }

    public void setItineraires(List<Itineraire> itineraires) {
        this.itineraires = itineraires;
    }

}