package com.example.springboot.first.app.repository;
import com.example.springboot.first.app.model.TypeVoyage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TypeVoyageRepository extends JpaRepository<TypeVoyage, Long> {
    List<TypeVoyage> findAll();
}