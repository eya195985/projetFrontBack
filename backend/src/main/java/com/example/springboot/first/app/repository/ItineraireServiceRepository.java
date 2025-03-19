package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.ItineraireService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItineraireServiceRepository extends JpaRepository<ItineraireService, Long> {

    List<ItineraireService> findByItineraireId(Long itineraireId);
}