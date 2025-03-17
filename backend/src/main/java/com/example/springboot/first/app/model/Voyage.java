package com.example.springboot.first.app.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "voyage")
public class Voyage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_voyage")
    private Long idVoyage;

    @Column(name = "titre_voyage", nullable = false)
    private String titreVoyage;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "prix", nullable = false)
    private Double prix;

    @Column(name = "duree", nullable = false)
    private Integer duree;

    @Column(name = "id_type_voyage", nullable = false)
    private Long idTypeVoyage;

    @ManyToOne
    @JoinColumn(name = "id_type_voyage", insertable = false, updatable = false)
    private TypeVoyage typeVoyage;

    @Enumerated(EnumType.STRING)
    @Column(name = "circuit", nullable = false)
    private CircuitEnum circuit;

    @Column(name = "taille_groupe")
    private Integer tailleGroupe;

    @Column(name = "tranche_age", nullable = false)
    private String trancheAge;

    @Enumerated(EnumType.STRING)
    @Column(name = "guide", nullable = false)
    private GuideEnum guide;

    @Column(name = "langue_guide")
    private String langueGuide;

    @ManyToOne
    @JoinColumn(name = "id_vol", nullable = false)
    private Vol vol;

    @Transient
    private Integer ageMin;

    @Transient
    private Integer ageMax;

    // Champ transient pour stocker les itinéraires (non persistant en base de données)
    @Transient
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
        this.idTypeVoyage = typeVoyage != null ? typeVoyage.getIdTypeVoyage() : null;
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

    // Getter et Setter pour le champ transient itineraires
    public List<Itineraire> getItineraires() {
        return itineraires;
    }

    public void setItineraires(List<Itineraire> itineraires) {
        this.itineraires = itineraires;
    }

    // Method to set ageMin and ageMax from trancheAge
    public void setAgeMinAndAgeMaxFromTrancheAge() {
        if (this.trancheAge != null && !this.trancheAge.isEmpty()) {
            String[] ages = this.trancheAge.split("-");
            if (ages.length == 2) {
                this.ageMin = Integer.parseInt(ages[0]);
                this.ageMax = Integer.parseInt(ages[1]);
            } else {
                throw new RuntimeException("Format de trancheAge invalide. Attendu : 'ageMin-ageMax'.");
            }
        }
    }

    // Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Voyage voyage = (Voyage) o;
        return Objects.equals(idVoyage, voyage.idVoyage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVoyage);
    }

    // ToString
    @Override
    public String toString() {
        return "Voyage{" +
                "idVoyage=" + idVoyage +
                ", titreVoyage='" + titreVoyage + '\'' +
                ", description='" + description + '\'' +
                ", prix=" + prix +
                ", duree=" + duree +
                ", idTypeVoyage=" + idTypeVoyage +
                ", typeVoyage=" + typeVoyage +
                ", circuit=" + circuit +
                ", tailleGroupe=" + tailleGroupe +
                ", trancheAge='" + trancheAge + '\'' +
                ", guide=" + guide +
                ", langueGuide='" + langueGuide + '\'' +
                ", vol=" + vol +
                ", itineraires=" + itineraires + // Ajout des itinéraires dans le toString
                '}';
    }
}