package com.example.springboot.first.app.controller;

import com.example.springboot.first.app.exception.ErrorResponse;
import com.example.springboot.first.app.exception.SuccessResponse;
import com.example.springboot.first.app.model.UserLogo;
import com.example.springboot.first.app.model.Utilisateur;
import com.example.springboot.first.app.payload.request.UpdateClientRequest;
import com.example.springboot.first.app.payload.response.ClientResponse;
import com.example.springboot.first.app.repository.UserLogoRepository;
import com.example.springboot.first.app.repository.UtilisateurRepository;
import com.example.springboot.first.app.security.JwtUtils;
import com.example.springboot.first.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UserLogoRepository userLogoRepository;

    @Autowired
    private ClientService clientService;


    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @GetMapping("/me")
    public ResponseEntity<ClientResponse> getClientByToken(@RequestHeader("Authorization") String token) {
        // Extraire l'ID de l'utilisateur du token
        Long utilisateurId = jwtUtils.getUtilisateurIdFromToken(token.replace("Bearer ", ""));

        if (utilisateurId != null) {
            ClientResponse response = clientService.getClientDetails(utilisateurId);

            if (response != null) {
                return ResponseEntity.ok(response); // Retourner la réponse avec un statut 200 OK
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retourner 404 si le client n'est pas trouvé
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Retourner 401 si le token est invalide
        }
    }


    @PostMapping("/logo")
    public ResponseEntity<?> uploadLogo(@RequestParam("logo") MultipartFile logo, @RequestHeader("Authorization") String token) {
        try {
            // Récupérer l'ID de l'utilisateur à partir du token
            Long userId = jwtUtils.getUtilisateurIdFromToken(token.replace("Bearer ", ""));

            // Vérifier si l'utilisateur existe
            Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(userId);
            if (utilisateurOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of("message", "Utilisateur non trouvé.")
                );
            }

            Utilisateur utilisateur = utilisateurOptional.get();

            // Supprimer l'ancien logo si existant
            if (utilisateur.getIdLogo() != null) {
                userLogoRepository.deleteById(UUID.fromString(utilisateur.getIdLogo()));
            }

            // Enregistrer le nouveau logo dans Cassandra
            UUID idLogo = UUID.randomUUID();
            ByteBuffer logoBytes = ByteBuffer.wrap(logo.getBytes());
            UserLogo userLogo = new UserLogo(idLogo, logoBytes);
            userLogoRepository.save(userLogo);

            // Mettre à jour l'ID du logo dans la table utilisateur
            utilisateur.setIdLogo(idLogo.toString());
            utilisateurRepository.save(utilisateur);

            // Retourner une réponse JSON
            return ResponseEntity.ok().body(
                    Map.of("message", "Logo mis à jour avec succès.")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Erreur lors de la mise à jour du logo : " + e.getMessage())
            );
        }
    }

    @GetMapping("/logo")
    public ResponseEntity<?> getLogo(@RequestHeader("Authorization") String token) {
        try {
            // Récupérer l'ID de l'utilisateur à partir du token
            Long userId = jwtUtils.getUtilisateurIdFromToken(token.replace("Bearer ", ""));

            // Vérifier si l'utilisateur existe
            Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(userId);
            if (utilisateurOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of("message", "Utilisateur non trouvé.")
                );
            }

            Utilisateur utilisateur = utilisateurOptional.get();

            // Récupérer le logo depuis Cassandra
            if (utilisateur.getIdLogo() != null) {
                Optional<UserLogo> userLogoOptional = userLogoRepository.findById(UUID.fromString(utilisateur.getIdLogo()));
                if (userLogoOptional.isPresent()) {
                    UserLogo userLogo = userLogoOptional.get();

                    // Retourner le logo sous forme de tableau de bytes
                    return ResponseEntity.ok()
                            .header("Content-Type", "image/png") // Ajustez le type MIME selon le format du logo
                            .body(userLogo.getLogo().array());
                }
            }

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "Logo non trouvé.")
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Erreur lors de la récupération du logo : " + e.getMessage())
            );
        }

    }

    @PutMapping("/update")
    public ResponseEntity<?> updateClientDetails(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateClientRequest updateRequest
    ) {
        try {
            // Vérifier que le token est valide
            if (token == null || !token.startsWith("Bearer ")) {
                System.out.println("Token invalide ou manquant.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Token invalide ou manquant."));
            }

            // Récupérer l'ID de l'utilisateur à partir du token
            Long userId = jwtUtils.getUtilisateurIdFromToken(token.replace("Bearer ", ""));
            System.out.println("User ID avant mise à jour : " + userId);
            System.out.println("Email avant mise à jour : " + updateRequest.getEmail());

            // Valider le nouvel email
            String newEmail = updateRequest.getEmail();
            if (newEmail == null || newEmail.isEmpty()) {
                System.out.println("L'email ne peut pas être vide.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("L'email ne peut pas être vide."));
            }

            // Mettre à jour les informations de l'utilisateur
            String result = clientService.updateClientDetails(userId, updateRequest);

            if (result.equals("Utilisateur non trouvé.")) {
                System.out.println("Utilisateur non trouvé avec l'ID : " + userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(result));
            }

            // Regénérer le token avec le nouvel email
            String newToken = jwtUtils.generateToken(userId, newEmail);
            System.out.println("Nouvel email : " + newEmail);
            System.out.println("Nouvel ID utilisateur : " + userId);
            System.out.println("Nouveau token généré : " + newToken);

            // Renvoyer le nouveau token dans l'en-tête de la réponse
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + newToken)
                    .body(new SuccessResponse("Informations mises à jour avec succès"));
        } catch (Exception e) {
            System.out.println("Erreur lors de la mise à jour des informations : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erreur lors de la mise à jour des informations : " + e.getMessage()));
        }
    }
}