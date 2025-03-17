package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.EscaleVol;
import com.example.springboot.first.app.model.EscaleVolId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EscaleVolRepository extends JpaRepository<EscaleVol, EscaleVolId> {
    // Méthodes personnalisées si nécessaire
}