package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.StatutUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StatutUtilisateurRepository extends JpaRepository<StatutUtilisateur, Long> {
    Optional<StatutUtilisateur> findByStatut(String statut);
}