package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.ClasseVol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClasseVolRepository extends JpaRepository<ClasseVol, Integer> {
    // Vous pouvez ajouter des méthodes personnalisées ici si nécessaire
}