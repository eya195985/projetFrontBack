package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);
    boolean existsByEmail(String email); // Vérifie si un utilisateur avec cet email existe déjà

    @Query("SELECT u FROM Utilisateur u LEFT JOIN FETCH u.statut WHERE u.idUtilisateur = :id")
    Optional<Utilisateur> findByIdWithStatut(@Param("id") Long id);

    @Query("SELECT u FROM Utilisateur u WHERE u.role.role = :role")
    List<Utilisateur> findByRole(@Param("role") String role);




}


