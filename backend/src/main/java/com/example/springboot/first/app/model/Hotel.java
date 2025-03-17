package com.example.springboot.first.app.model;


import jakarta.persistence.*;

@Entity
@Table(name = "Hôtel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_hôtel")
    private Long id;

    @Column(name = "nom_hôtel", nullable = false)
    private String nomHotel;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "prix_adulte")
    private Double prixAdulte;

    @Column(name = "prix_enfant")
    private Double prixEnfant;

    @Column(name = "capacité")
    private Integer capacite;

    @ManyToOne
    @JoinColumn(name = "id_type_hôtel")
    private TypeHotel typeHotel;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomHotel() {
        return nomHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Double getPrixAdulte() {
        return prixAdulte;
    }

    public void setPrixAdulte(Double prixAdulte) {
        this.prixAdulte = prixAdulte;
    }

    public Double getPrixEnfant() {
        return prixEnfant;
    }

    public void setPrixEnfant(Double prixEnfant) {
        this.prixEnfant = prixEnfant;
    }

    public Integer getCapacite() {
        return capacite;
    }

    public void setCapacite(Integer capacite) {
        this.capacite = capacite;
    }

    public TypeHotel getTypeHotel() {
        return typeHotel;
    }

    public void setTypeHotel(TypeHotel typeHotel) {
        this.typeHotel = typeHotel;
    }
}
