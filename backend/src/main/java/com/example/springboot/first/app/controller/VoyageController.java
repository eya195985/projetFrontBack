package com.example.springboot.first.app.controller;

import com.example.springboot.first.app.model.*;
import com.example.springboot.first.app.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class VoyageController {

    private static final Logger logger = LoggerFactory.getLogger(VoyageController.class);

    @Autowired
    private VoyageService voyageService;

    @Autowired
    private PaysService paysService;

    @Autowired
    private ClasseVolService classeVolService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private TransportService transportService;

    @Autowired
    private TypeTransportService typeTransportService;

    @Autowired
    private TypeHotelService typeHotelService;

    @Autowired
    private TypeNourritureService typeNourritureService;

    @Autowired
    private ConcessionnaireService concessionnaireService;

    @Autowired
    private UtilisateurService utilisateurService;

    // ==================== Endpoints pour les voyages ====================

    @GetMapping("/voyages/types")
    public ResponseEntity<List<TypeVoyage>> getAllTypesVoyage() {
        return ResponseEntity.ok(voyageService.getAllTypesVoyage());
    }

    @GetMapping("/voyages/pays")
    public ResponseEntity<List<Pays>> getAllPays() {
        return ResponseEntity.ok(paysService.getAllPays());
    }

    @GetMapping("/voyages/classes-vol")
    public ResponseEntity<List<ClasseVol>> getClassesVol() {
        return ResponseEntity.ok(classeVolService.getAllClassesVol());
    }

    @GetMapping("/voyages/regions/{idPays}")
    public ResponseEntity<List<Region>> getRegionsByPays(@PathVariable Long idPays) {
        List<Region> regions = regionService.getRegionsByPays(idPays);
        return regions.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(regions);
    }

    @GetMapping("/voyages/transports")
    public ResponseEntity<List<Transport>> getTransports() {
        return ResponseEntity.ok(transportService.getAllTransports());
    }

    @GetMapping("/voyages/type-transports")
    public ResponseEntity<List<TypeTransport>> getTypeTransports() {
        return ResponseEntity.ok(typeTransportService.getAllTypeTransports());
    }

    @GetMapping("/voyages/hotel-types")
    public ResponseEntity<List<TypeHotel>> getHotelTypes() {
        return ResponseEntity.ok(typeHotelService.getAllHotelTypes());
    }

    @GetMapping("/voyages/nourriture-types")
    public ResponseEntity<List<TypeNourriture>> getNourritureTypes() {
        return ResponseEntity.ok(typeNourritureService.getAllNourritureTypes());
    }

    // ==================== Endpoints pour les concessionnaires ====================

    @GetMapping("/concessionnaires")
    public ResponseEntity<List<Concessionnaire>> getAllConcessionnaires() {
        return ResponseEntity.ok(concessionnaireService.getAllConcessionnaires());
    }



    @GetMapping("/concessionnaires/filter-by-user-status")
    public ResponseEntity<List<Concessionnaire>> getConcessionnairesByUtilisateurStatut(@RequestParam String statut) {
        logger.info("Filtering concessionnaires by user status: {}", statut);
        List<Concessionnaire> concessionnaires = concessionnaireService.getConcessionnairesByUtilisateurStatut(statut);
        return concessionnaires.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(concessionnaires);
    }

    @GetMapping("/concessionnaires/{id}")
    public ResponseEntity<Concessionnaire> getConcessionnaireById(@PathVariable Long id) {
        logger.info("Fetching concessionnaire by ID: {}", id);
        return concessionnaireService.getConcessionnaireById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Concessionnaire not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/concessionnaires/{id}/status")
    public ResponseEntity<Map<String, String>> updateConcessionnaireStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        logger.info("Updating concessionnaire status for ID: {}", id);
        String status = request.get("status");
        try {
            concessionnaireService.updateConcessionnaireStatus(id, status);
            return ResponseEntity.ok(Collections.singletonMap("message", "Status updated successfully"));
        } catch (RuntimeException e) {
            logger.error("Error updating concessionnaire status: {}", e.getMessage());
            return ResponseEntity.status(404).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    // ==================== Endpoints pour les utilisateurs ====================

    @GetMapping("/utilisateurs")
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Utilisateur>> getAllClients() {
        return ResponseEntity.ok(utilisateurService.getAllClients());
    }


    @GetMapping("/utilisateurs/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        logger.info("Fetching utilisateur by ID: {}", id);
        return utilisateurService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    logger.warn("Utilisateur not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }


}











