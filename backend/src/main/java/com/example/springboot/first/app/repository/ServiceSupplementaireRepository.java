package com.example.springboot.first.app.repository;


import com.example.springboot.first.app.model.ServiceSupplementaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceSupplementaireRepository extends JpaRepository<ServiceSupplementaire, Long> {
}