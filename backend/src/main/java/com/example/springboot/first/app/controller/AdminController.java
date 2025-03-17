package com.example.springboot.first.app.controller;

import com.example.springboot.first.app.payload.response.ClientResponse;
import com.example.springboot.first.app.payload.response.ConcessionnaireResponse;
import com.example.springboot.first.app.service.ClientService;
import com.example.springboot.first.app.service.ConcessionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private ConcessionnaireService concessionnaireService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/en-attente")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // Exiger le rôle ADMIN
    public ResponseEntity<List<ConcessionnaireResponse>> getConcessionnairesEnAttente() {
        List<ConcessionnaireResponse> responses = concessionnaireService.getConcessionnairesEnAttente();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/statut")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateStatutConcessionnaire(@RequestBody ConcessionnaireResponse request) {
        try {
            System.out.println("hetha email : " + request.getEmail());
            System.out.println("hethi statut " + request.getStatut());
            concessionnaireService.updateStatut(request.getEmail(), request.getStatut());
            return ResponseEntity.ok("Statut mis à jour avec succès");
        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'erreur exacte dans la console
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour du statut : " + e.getMessage());
        }
    }

    @PutMapping("/statut/client")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateStatutClient(@RequestBody ClientResponse request) {
        try {

            clientService.updateStatut(request.getIdUtilisateur(), request.getStatut());
            return ResponseEntity.ok("Statut mis à jour avec succès");
        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'erreur exacte dans la console
            return ResponseEntity.badRequest().body("Erreur lors de la mise à jour du statut : " + e.getMessage());
        }
    }


}