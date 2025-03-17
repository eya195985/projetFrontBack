package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.ItineraireService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraireServiceRepository extends JpaRepository<ItineraireService, Long> {
}