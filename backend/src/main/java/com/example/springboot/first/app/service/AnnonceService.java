package com.example.springboot.first.app.service;

import com.example.springboot.first.app.model.Annonce;
import com.example.springboot.first.app.model.Itineraire;
import com.example.springboot.first.app.model.ItineraireService;
import com.example.springboot.first.app.model.StatutAnnonce;
import com.example.springboot.first.app.repository.AnnonceRepository;
import com.example.springboot.first.app.repository.ItineraireRepository;
import com.example.springboot.first.app.repository.ItineraireServiceRepository;
import com.example.springboot.first.app.repository.StatutAnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private StatutAnnonceRepository statutAnnonceRepository;

    public List<Annonce> getAnnoncesEnAttente() {
        return annonceRepository.findByStatutAnnonce_Statut("En attente");
    }



    public Optional<Annonce> getAnnonceById(Long annonceId) {
        return annonceRepository.findById(annonceId);
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

    //------iteneraire------------//
    @Autowired
    private ItineraireRepository itineraireRepository;

    @Autowired
    private ItineraireServiceRepository itineraireServiceRepository;


    public List<Itineraire> getItinerairesByVoyageId(Long voyageId) {
        // Récupérer les itinéraires associés au voyageId
        List<Itineraire> itineraires = itineraireRepository.findByVoyageIdVoyage(voyageId);

        // Pour chaque itinéraire, récupérer les services associés
        itineraires.forEach(itineraire -> {
            List<ItineraireService> itineraireServices = itineraireServiceRepository.findByItineraireId(itineraire.getId());
            itineraire.setServices(itineraireServices.stream()
                    .map(ItineraireService::getService)
                    .collect(Collectors.toList()));
        });

        return itineraires;
    }
}