package com.example.springboot.first.app.payload.response;

public class JwtResponse {
    private String token; // Token JWT
    private String role; // Rôle de l'utilisateur
    private String email; // Email de l'utilisateur
    private Long utilisateurId; // ID de l'utilisateur

    // Constructeur pour la réponse JWT
    public JwtResponse(String token, String role, String email, Long utilisateurId) {
        this.token = token;
        this.role = role;
        this.email = email;
        this.utilisateurId = utilisateurId;
    }

    // Getters et Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Long utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
}