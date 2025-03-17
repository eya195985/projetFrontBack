package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Nourriture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NourritureRepository extends JpaRepository<Nourriture, Long> {
}
