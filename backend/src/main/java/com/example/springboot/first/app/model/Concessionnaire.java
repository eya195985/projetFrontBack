
package com.example.springboot.first.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "concessionnaire")
public class Concessionnaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_concessionnaire")
    private Long idConcessionnaire;

    @Column(name = "site_web")
    private String siteWeb;

    @Column(name = "numero_tax")
    private String numeroTax;

    @Column(name = "licence_commerciale")
    private String licenceCommerciale;

    @Column(name = "description")
    private String description;

    @Column(name = "nom_gerant", nullable = false)
    private String nomGerant;

    @Column(name = "email_gerant", nullable = false, unique = true)
    private String emailGerant;

    @Column(name = "telephone_gerant", nullable = false)
    private String telephoneGerant;

    @Column(name = "paye")
    private String paye;

    @ManyToOne
    @JoinColumn(name = "id_utilisateur", nullable = false, unique = true)
    private Utilisateur utilisateur; // Relation Many-to-One avec Utilisateur

    // Getters et Setters
    public Long getIdConcessionnaire() {
        return idConcessionnaire;
    }

    public void setIdConcessionnaire(Long idConcessionnaire) {
        this.idConcessionnaire = idConcessionnaire;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getNumeroTax() {
        return numeroTax;
    }

    public void setNumeroTax(String numeroTax) {
        this.numeroTax = numeroTax;
    }

    public String getLicenceCommerciale() {
        return licenceCommerciale;
    }

    public void setLicenceCommerciale(String licenceCommerciale) {
        this.licenceCommerciale = licenceCommerciale;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNomGerant() {
        return nomGerant;
    }

    public void setNomGerant(String nomGerant) {
        this.nomGerant = nomGerant;
    }

    public String getEmailGerant() {
        return emailGerant;
    }

    public void setEmailGerant(String emailGerant) {
        this.emailGerant = emailGerant;
    }

    public String getTelephoneGerant() {
        return telephoneGerant;
    }

    public void setTelephoneGerant(String telephoneGerant) {
        this.telephoneGerant = telephoneGerant;
    }

    public String getPaye() {
        return paye;
    }

    public void setPaye(String paye) {
        this.paye = paye;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}