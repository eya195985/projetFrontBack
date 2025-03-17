package com.example.springboot.first.app.service;

import com.example.springboot.first.app.model.TypeNourriture;
import com.example.springboot.first.app.repository.TypeNourritureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeNourritureService {

    @Autowired
    private TypeNourritureRepository typeNourritureRepository;

    public List<TypeNourriture> getAllNourritureTypes() {
        return typeNourritureRepository.findAll();
    }
}
