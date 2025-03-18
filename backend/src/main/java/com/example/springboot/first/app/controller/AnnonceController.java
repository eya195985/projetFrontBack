package com.example.springboot.first.app.controller;
import com.example.springboot.first.app.model.Annonce;
import com.example.springboot.first.app.service.AnnonceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/annonces")
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

    @GetMapping("/En-attente")
    public ResponseEntity<List<Annonce>> getAnnoncesEnAttente() {
        List<Annonce> annonces = annonceService.getAnnoncesEnAttente();
        return ResponseEntity.ok(annonces);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Map<String, String>> updateAnnonceStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request
    ) {
        String statut = request.get("statut");
        try {
            annonceService.updateAnnonceStatus(id, statut);
            return ResponseEntity.ok(Map.of("message", "Statut de l'annonce mis à jour avec succès"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}