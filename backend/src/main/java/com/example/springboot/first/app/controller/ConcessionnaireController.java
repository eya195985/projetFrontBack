package com.example.springboot.first.app.controller;

import com.example.springboot.first.app.exception.ErrorResponse;
import com.example.springboot.first.app.model.Concessionnaire;
import com.example.springboot.first.app.model.UserLogo;
import com.example.springboot.first.app.model.Utilisateur;
import com.example.springboot.first.app.payload.request.ChangePasswordRequest;
import com.example.springboot.first.app.payload.request.UpdateConcessionnaireRequest;
import com.example.springboot.first.app.payload.response.ConcessionnaireResponse;
import com.example.springboot.first.app.repository.ConcessionnaireRepository;
import com.example.springboot.first.app.exception.InvalidPasswordException;
import com.example.springboot.first.app.repository.UserLogoRepository;
import com.example.springboot.first.app.repository.UtilisateurRepository;
import com.example.springboot.first.app.security.JwtUtils;
import com.example.springboot.first.app.service.ConcessionnaireService;
import com.example.springboot.first.app.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;
import java.util.Map;

@RestController
@RequestMapping("/api/concessionnaires")
public class ConcessionnaireController {

    @Autowired
    private ConcessionnaireRepository concessionnaireRepository;

    @Autowired
    private UserLogoRepository userLogoRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ConcessionnaireService concessionnaireService;



    @GetMapping("/by-user-id/{userId}")
    public ResponseEntity<ConcessionnaireResponse> getConcessionnaireByUserId(
            @PathVariable Long userId,
            @RequestHeader("Authorization") String token
    ) {
        Long utilisateurIdFromToken = jwtUtils.getUtilisateurIdFromToken(token.replace("Bearer ", ""));
        if (utilisateurIdFromToken == null || !utilisateurIdFromToken.equals(userId)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(userId);
        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();
            ConcessionnaireResponse response = concessionnaireService.getConcessionnaireDetails(utilisateur);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




    @GetMapping("/logo/{idLogo}")
    public ResponseEntity<byte[]> getLogo(@PathVariable String idLogo) {
        UserLogo userLogo = userLogoRepository.findById(UUID.fromString(idLogo)).orElse(null);
        if (userLogo != null && userLogo.getLogo() != null) {
            byte[] logoBytes = new byte[userLogo.getLogo().remaining()];
            userLogo.getLogo().get(logoBytes);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG) // Ajustez le type MIME selon le format de l'image
                    .body(logoBytes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            @RequestHeader("Authorization") String token
    ) {
        // Extraire l'ID de l'utilisateur du token JWT
        Long utilisateurId = jwtUtils.getUtilisateurIdFromToken(token.replace("Bearer ", ""));

        if (utilisateurId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Token invalide."));
        }

        // Vérifier et mettre à jour le mot de passe
        try {
            utilisateurService.changePassword(utilisateurId, request.getOldPassword(), request.getNewPassword());
            return ResponseEntity.ok().body(Map.of("message", "Mot de passe mis à jour avec succès."));
        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Ancien mot de passe incorrect."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Une erreur est survenue lors du changement de mot de passe."));
        }
    }


    //------------------------------update concessionaire -----------------//
    @PutMapping("/update-agency")
    public ResponseEntity<?> updateConcessionnaire(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateConcessionnaireRequest updateRequest
    ) {
        try {
            System.out.println("Début de la mise à jour du concessionnaire...");

            // Validate the token
            if (token == null || !token.startsWith("Bearer ")) {
                System.out.println("Token invalide ou manquant.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Token invalide ou manquant."));
            }

            // Extract user ID from the token
            Long userId = jwtUtils.getUtilisateurIdFromToken(token.replace("Bearer ", ""));
            System.out.println("ID utilisateur extrait du token : " + userId);

            if (userId == null) {
                System.out.println("Token invalide : impossible d'extraire l'ID utilisateur.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Token invalide."));
            }

            // Validate the request
            if (updateRequest.getEmail() == null || updateRequest.getEmail().isEmpty()) {
                System.out.println("L'email ne peut pas être vide.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("L'email ne peut pas être vide."));
            }



            // Update the concessionnaire details
            System.out.println("Appel du service pour mettre à jour le concessionnaire...");
            ConcessionnaireResponse response = concessionnaireService.updateConcessionnaire(userId, updateRequest);

            // Generate a new token with the updated email
            System.out.println("Génération d'un nouveau token avec l'email : " + updateRequest.getEmail());
            String newToken = jwtUtils.generateToken(userId, updateRequest.getEmail());

            // Return the response with the new token
            System.out.println("Mise à jour réussie. Renvoi de la réponse...");
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + newToken)
                    .body(response);
        } catch (Exception e) {
            // Log the error
            System.out.println("Erreur lors de la mise à jour du concessionnaire : " + e.getMessage());
            e.printStackTrace();

            // Return a 500 Internal Server Error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erreur lors de la mise à jour du concessionnaire : " + e.getMessage()));
        }
    }
}
