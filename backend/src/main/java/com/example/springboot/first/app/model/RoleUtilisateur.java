package com.example.springboot.first.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "role_utilisateur")
public class RoleUtilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;

    @Column(name = "role", nullable = false)
    private String role; // "client", "agence", "admin"

    // Getters et Setters

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}