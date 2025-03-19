package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Itineraire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItineraireRepository extends JpaRepository<Itineraire, Long> {

    List<Itineraire> findByVoyageIdVoyage(Long voyageId);
}