package com.example.springboot.first.app.service;

import com.example.springboot.first.app.model.Annonce;
import com.example.springboot.first.app.model.StatutAnnonce;
import com.example.springboot.first.app.repository.AnnonceRepository;
import com.example.springboot.first.app.repository.StatutAnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private StatutAnnonceRepository statutAnnonceRepository;

    public List<Annonce> getAnnoncesEnAttente() {
        return annonceRepository.findByStatutAnnonce_Statut("En attente");
    }

    public void updateAnnonceStatus(Long idAnnonce, String statut) {
        Optional<Annonce> annonceOpt = annonceRepository.findById(idAnnonce);
        if (annonceOpt.isPresent()) {
            Annonce annonce = annonceOpt.get();
            StatutAnnonce statutAnnonce = statutAnnonceRepository.findByStatut(statut)
                    .orElseThrow(() -> new RuntimeException("Statut non trouvé : " + statut));
            annonce.setStatutAnnonce(statutAnnonce);
            annonceRepository.save(annonce);
        } else {
            throw new RuntimeException("Annonce non trouvée avec l'ID : " + idAnnonce);
        }
    }
}