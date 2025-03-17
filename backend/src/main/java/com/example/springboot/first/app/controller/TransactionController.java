package com.example.springboot.first.app.controller;

import com.example.springboot.first.app.model.Utilisateur;
import com.example.springboot.first.app.model.Voyage;
import com.example.springboot.first.app.repository.UtilisateurRepository;
import com.example.springboot.first.app.security.JwtUtils;
import com.example.springboot.first.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@RestController
@RequestMapping("/api/voyages")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    // Méthode pour valider le format de la date
    private boolean isValidDate(String dateStr) {
        try {
            DateTimeFormatter.ISO_DATE_TIME.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    @PreAuthorize("hasRole('ROLE_CONCESSIONAIRE')")
    @PostMapping
    public ResponseEntity<?> createVoyage(@RequestBody Map<String, Object> voyageData ,@RequestHeader("Authorization") String token) {
        try {

            Long userId = jwtUtils.getUtilisateurIdFromToken(token.replace("Bearer ", ""));
            Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(userId);
            Utilisateur utilisateur = utilisateurOptional.get();
            // Log the received voyage data for debugging
            System.out.println("Received Voyage Data: " + voyageData);

            // Valider les dates avant de les envoyer au service
            if (voyageData.containsKey("vol")) {
                Map<String, Object> volData = (Map<String, Object>) voyageData.get("vol");

                // Valider dateDepart
                if (volData.containsKey("dateDepart")) {
                    String dateDepart = (String) volData.get("dateDepart");
                    if (!isValidDate(dateDepart)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Format de dateDepart invalide. Le format attendu est : yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    }
                }

                // Valider dateArrivee
                if (volData.containsKey("dateArrivee")) {
                    String dateArrivee = (String) volData.get("dateArrivee");
                    if (!isValidDate(dateArrivee)) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Format de dateArrivee invalide. Le format attendu est : yyyy-MM-dd'T'HH:mm:ss.SSSZ");
                    }
                }
            }

            // Si les dates sont valides, passer les données au service
            Voyage savedVoyage = transactionService.saveVoyageData(voyageData,utilisateur);
            return ResponseEntity.ok(savedVoyage);
        } catch (Exception e) {
            // Gérer les autres erreurs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite : " + e.getMessage());
        }
    }
}