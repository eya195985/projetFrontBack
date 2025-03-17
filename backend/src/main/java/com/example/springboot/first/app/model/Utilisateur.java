package com.example.springboot.first.app.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur")
    private Long idUtilisateur;

    @Column(name = "nom_complet", nullable = false)
    private String nomComplet;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "mot_de_passe", nullable = true)
    private String motDePasse;

    @Column(name = "date_inscription", nullable = false)
    private Timestamp dateInscription = new Timestamp(System.currentTimeMillis());

    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private RoleUtilisateur role;

    @ManyToOne
    @JoinColumn(name = "id_statut", nullable = false)
    private StatutUtilisateur statut;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "id_logo")
    private String idLogo; // Référence au logo stocké dans Cassandra

    @Column(name = "adresse")
    private String adresse;

    // Getters et Setters

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

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Timestamp getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(Timestamp dateInscription) {
        this.dateInscription = dateInscription;
    }

    public RoleUtilisateur getRole() {
        return role;
    }

    public void setRole(RoleUtilisateur role) {
        this.role = role;
    }

    public StatutUtilisateur getStatut() {
        return statut;
    }

    public void setStatut(StatutUtilisateur statut) {
        this.statut = statut;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdLogo() {
        return idLogo;
    }

    public void setIdLogo(String idLogo) {
        this.idLogo = idLogo;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}