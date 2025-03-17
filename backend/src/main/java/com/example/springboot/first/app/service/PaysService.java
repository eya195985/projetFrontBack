package com.example.springboot.first.app.service;

import com.example.springboot.first.app.model.Pays;
import com.example.springboot.first.app.repository.PaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaysService {

    @Autowired
    private PaysRepository paysRepository;

    // Récupérer tous les pays
    public List<Pays> getAllPays() {
        return paysRepository.findAllByOrderByNomPaysAsc();
    }
}