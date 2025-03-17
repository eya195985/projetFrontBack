package com.example.springboot.first.app.payload.request;

import org.springframework.web.multipart.MultipartFile;

public class ConcessionnaireSignupRequest {
    // Champs de l'utilisateur
    private String email;
    private String nomComplet;
    private String telephone;
    private String adresse;

    // Champs spécifiques au concessionnaire
    private String siteWeb;
    private String numeroTax;
    private String licenceCommerciale;
    private String description;
    private String nomGerant;
    private String emailGerant;
    private String telephoneGerant;
    private String paye;
    private MultipartFile logo;

    // Getters et Setters pour les champs de l'utilisateur
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
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

    // Getters et Setters pour les champs spécifiques au concessionnaire
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

    public MultipartFile getLogo() {
        return logo;
    }

    public void setLogo(MultipartFile logo) {
        this.logo = logo;
    }
}