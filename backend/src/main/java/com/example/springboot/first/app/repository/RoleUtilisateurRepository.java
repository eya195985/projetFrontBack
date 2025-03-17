package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.RoleUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleUtilisateurRepository extends JpaRepository<RoleUtilisateur, Long> {
    Optional<RoleUtilisateur> findByRoleIgnoreCase(String role);
    List<RoleUtilisateur> findAll();
}
