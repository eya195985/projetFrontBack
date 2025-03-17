package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.Supplementaires;
import com.example.springboot.first.app.model.SupplementairesId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplementairesRepository extends JpaRepository<Supplementaires, SupplementairesId> {
}