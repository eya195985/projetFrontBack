package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Concessionnaire;
import com.example.springboot.first.app.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConcessionnaireRepository extends JpaRepository<Concessionnaire, Long> {
    // Utilisez "utilisateur.idUtilisateur" pour la recherche
    Optional<Concessionnaire> findByUtilisateurIdUtilisateur(Long idUtilisateur);

    // Rechercher un concessionnaire par l'email de l'utilisateur associé
    @Query("SELECT c FROM Concessionnaire c JOIN c.utilisateur u WHERE u.email = :email")
    Optional<Concessionnaire> findByUtilisateurEmail(@Param("email") String email);

    Optional<Concessionnaire> findByUtilisateur(Utilisateur utilisateur);

    boolean existsByNumeroTax(String numeroTax);
    boolean existsByLicenceCommerciale(String licenceCommerciale);


    @Query("SELECT c FROM Concessionnaire c JOIN c.utilisateur u JOIN u.statut s WHERE s.statut = :statut")
    List<Concessionnaire> findByUtilisateur_Statut_Statut(@Param("statut") String statut);
    /*---------------------------------------------*/

    @Query("SELECT c FROM Concessionnaire c JOIN c.utilisateur u JOIN u.statut su WHERE su.statut = :statut")
    List<Concessionnaire> findByUtilisateurStatutUtilisateurStatut(@Param("statut") String statut);

    // Récupérer tous les concessionnaires
    List<Concessionnaire> findAll();


}