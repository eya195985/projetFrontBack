package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoyageRepository extends JpaRepository<Voyage, Long> {
}