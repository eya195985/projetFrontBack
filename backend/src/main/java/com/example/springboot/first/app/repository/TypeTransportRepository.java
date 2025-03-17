package com.example.springboot.first.app.repository;

import com.example.springboot.first.app.model.TypeTransport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeTransportRepository extends JpaRepository<TypeTransport, Integer> {
    // Vous pouvez ajouter des méthodes de requête personnalisées ici si nécessaire
}

