package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.ServiceNourriture;
import com.example.springboot.first.app.model.ServiceNourritureId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceNourritureRepository extends JpaRepository<ServiceNourriture, ServiceNourritureId> {
}