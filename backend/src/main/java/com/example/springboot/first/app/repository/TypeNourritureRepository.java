package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.TypeNourriture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeNourritureRepository extends JpaRepository<TypeNourriture, Integer> {
}
