package com.example.springboot.first.app.payload.response;

public class ConcessionnaireResponse {

    private Long idConcessionnaire;
    private Long idUtilisateur;
    private String nomComplet;
    private String email;
    private String telephone;
    private String adresse;
    private String siteWeb;
    private String numeroTax;
    private String licenceCommerciale;
    private String description;
    private String nomGerant;
    private String emailGerant;
    private String telephoneGerant;
    private String paye;
    private String statut;
    private String idLogo; // Nouveau champ pour l'ID du logo
    private byte[] logo; // Nouveau champ pour les données binaires du logo


    // Getters et Setters
    public Long getIdConcessionnaire() {
        return idConcessionnaire;
    }

    public void setIdConcessionnaire(Long idConcessionnaire) {
        this.idConcessionnaire = idConcessionnaire;
    }

    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getIdLogo() {
        return idLogo;
    }

    public void setIdLogo(String idLogo) {
        this.idLogo = idLogo;
    }


    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}