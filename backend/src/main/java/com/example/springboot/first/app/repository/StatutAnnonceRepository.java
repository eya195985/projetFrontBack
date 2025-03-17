package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.StatutAnnonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatutAnnonceRepository extends JpaRepository<StatutAnnonce, Long> {
    Optional<StatutAnnonce> findByStatut(String statut);
}