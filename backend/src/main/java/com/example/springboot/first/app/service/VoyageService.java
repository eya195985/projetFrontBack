package com.example.springboot.first.app.service;

import com.example.springboot.first.app.model.TypeVoyage;
import com.example.springboot.first.app.repository.TypeVoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageService {

    private final TypeVoyageRepository typeVoyageRepository;

    @Autowired
    public VoyageService(TypeVoyageRepository typeVoyageRepository) {
        this.typeVoyageRepository = typeVoyageRepository;
    }

    /**
     * Récupère tous les types de voyage depuis la base de données.
     *
     * @return Une liste de tous les types de voyage.
     */
    public List<TypeVoyage> getAllTypesVoyage() {
        return typeVoyageRepository.findAll();
    }



}