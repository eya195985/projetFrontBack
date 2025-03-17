package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaysRepository extends JpaRepository<Pays, Long> {
    // Récupérer tous les pays triés par nom
    List<Pays> findAllByOrderByNomPaysAsc();
}