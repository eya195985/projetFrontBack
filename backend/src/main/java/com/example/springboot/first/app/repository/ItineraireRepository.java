package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Itineraire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraireRepository extends JpaRepository<Itineraire, Long> {
}