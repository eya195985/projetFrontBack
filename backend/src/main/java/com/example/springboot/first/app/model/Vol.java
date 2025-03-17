package com.example.springboot.first.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "vol")
public class Vol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vol")
    private Long idVol;

    @Column(name = "numero_vol", nullable = false)
    private String numeroVol;

    @Column(name = "nom_compagnie_aerienne", nullable = false)
    private String nomCompagnieAerienne;

    @Column(name = "pays_depart", nullable = false)
    private String paysDepart;

    @Column(name = "pays_arrivee", nullable = false)
    private String paysArrivee;

    @Column(name = "date_depart", nullable = false)
    private String dateDepart; // Stocker la date en tant que String

    @Column(name = "date_arrivee", nullable = false)
    private String dateArrivee; // Stocker la date en tant que String

    // Getters et setters
    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(String dateArrivee) {
        this.dateArrivee = dateArrivee;
    }
    // Getters et setters
    @Column(name = "classe_vol_id", nullable = false)
    private Integer classeVolId;

    @ManyToOne
    @JoinColumn(name = "classe_vol_id", insertable = false, updatable = false)
    private ClasseVol classeVol;

    @Enumerated(EnumType.STRING)
    @Column(name = "escale", nullable = false)
    private EscaleEnum escale;

    @OneToMany(mappedBy = "vol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EscaleVol> escales;

    @OneToMany(mappedBy = "vol", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Voyage> voyages;

    @Transient // Ce champ n'est pas persisté en base de données
    private List<Long> escales_aller;

    // Getters et setters
    public Long getIdVol() {
        return idVol;
    }

    public void setIdVol(Long idVol) {
        this.idVol = idVol;
    }

    public String getNumeroVol() {
        return numeroVol;
    }

    public void setNumeroVol(String numeroVol) {
        this.numeroVol = numeroVol;
    }

    public String getNomCompagnieAerienne() {
        return nomCompagnieAerienne;
    }

    public void setNomCompagnieAerienne(String nomCompagnieAerienne) {
        this.nomCompagnieAerienne = nomCompagnieAerienne;
    }

    public String getPaysDepart() {
        return paysDepart;
    }

    public void setPaysDepart(String paysDepart) {
        this.paysDepart = paysDepart;
    }

    public String getPaysArrivee() {
        return paysArrivee;
    }

    public void setPaysArrivee(String paysArrivee) {
        this.paysArrivee = paysArrivee;
    }

    public Integer getClasseVolId() {
        return classeVolId;
    }

    public void setClasseVolId(Integer classeVolId) {
        this.classeVolId = classeVolId;
    }

    public ClasseVol getClasseVol() {
        return classeVol;
    }

    public void setClasseVol(ClasseVol classeVol) {
        this.classeVol = classeVol;
        this.classeVolId = classeVol != null ? classeVol.getIdClasseVoyage() : null;
    }

    public EscaleEnum getEscale() {
        return escale;
    }

    public void setEscale(EscaleEnum escale) {
        this.escale = escale;
    }

    public List<EscaleVol> getEscales() {
        return escales;
    }

    public void setEscales(List<EscaleVol> escales) {
        this.escales = escales;
    }

    public List<Voyage> getVoyages() {
        return voyages;
    }

    public void setVoyages(List<Voyage> voyages) {
        this.voyages = voyages;
    }

    public List<Long> getEscales_aller() {
        return escales_aller;
    }

    public void setEscales_aller(List<Long> escales_aller) {
        this.escales_aller = escales_aller;
    }

    // Méthodes utilitaires
    public void addEscale(EscaleVol escale) {
        escales.add(escale);
        escale.setVol(this);
    }

    public void removeEscale(EscaleVol escale) {
        escales.remove(escale);
        escale.setVol(null);
    }

    // Equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vol vol = (Vol) o;
        return Objects.equals(idVol, vol.idVol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVol);
    }

    // ToString
    @Override
    public String toString() {
        return "Vol{" +
                "idVol=" + idVol +
                ", numeroVol='" + numeroVol + '\'' +
                ", nomCompagnieAerienne='" + nomCompagnieAerienne + '\'' +
                ", paysDepart='" + paysDepart + '\'' +
                ", paysArrivee='" + paysArrivee + '\'' +
                ", dateDepart=" + dateDepart +
                ", dateArrivee=" + dateArrivee +
                ", classeVolId=" + classeVolId +
                ", escale=" + escale +
                '}';
    }
}