package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

    @Query("SELECT a FROM Annonce a JOIN FETCH a.concessionnaire JOIN FETCH a.voyage JOIN FETCH a.statutAnnonce WHERE a.statutAnnonce.statut = :statut")
    List<Annonce> findByStatutAnnonce_Statut(@Param("statut") String statut);
}