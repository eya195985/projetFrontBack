package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceRepository extends JpaRepository<Services, Long> {
}
