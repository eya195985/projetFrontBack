package com.example.springboot.first.app.controller;

import com.example.springboot.first.app.exception.EmailAlreadyUsedException;
import com.example.springboot.first.app.exception.EmailNotFoundException;
import com.example.springboot.first.app.model.RoleUtilisateur;
import com.example.springboot.first.app.payload.request.ClientSignupRequest;
import com.example.springboot.first.app.payload.request.ConcessionnaireSignupRequest;
import com.example.springboot.first.app.payload.request.LoginRequest;
import com.example.springboot.first.app.payload.request.VerificationRequest;
import com.example.springboot.first.app.payload.response.JwtResponse;
import com.example.springboot.first.app.service.ConcessionnaireService;
import com.example.springboot.first.app.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import com.example.springboot.first.app.model.Utilisateur;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.example.springboot.first.app.security.JwtUtils;
import com.example.springboot.first.app.service.UtilisateurService;
import com.example.springboot.first.app.repository.UtilisateurRepository;
import org.springframework.http.HttpStatus;
import com.example.springboot.first.app.exception.ErrorResponse;
import com.example.springboot.first.app.exception.SuccessResponse;
import com.example.springboot.first.app.service.EmailService;
import org.springframework.web.multipart.MultipartFile;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UtilisateurRepository utilisateurRepository; // Injectez le repository

    @Autowired
    private ConcessionnaireService concessionnaireService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Authentifier l'utilisateur
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // Log pour vérifier l'authentification
            System.out.println("Authentification réussie pour l'email : " + loginRequest.getEmail());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Récupérer l'utilisateur à partir de l'email
            Utilisateur utilisateur = utilisateurRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email : " + loginRequest.getEmail()));

            // Log pour vérifier les détails de l'utilisateur récupéré
            System.out.println("Utilisateur trouvé : " + utilisateur.getEmail());
            System.out.println("Rôle de l'utilisateur : " + utilisateur.getRole().getRole());
            System.out.println("Statut de l'utilisateur : " + utilisateur.getStatut().getStatut());

            // Vérifier le statut de l'utilisateur
            String statut = utilisateur.getStatut().getStatut();
            if ("en attente".equalsIgnoreCase(statut)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ErrorResponse("Votre compte est en attente de validation."));
            } else if ("Rejeter".equalsIgnoreCase(statut)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ErrorResponse("Votre compte a été Rejeter."));
            } else if ("Bloquer".equalsIgnoreCase(statut)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(new ErrorResponse("Votre compte a été bloqué."));
            }

            // Générer un token JWT avec l'ID de l'utilisateur (sans agenceId)
            String token = jwtUtils.generateToken(authentication, utilisateur.getIdUtilisateur(), utilisateur.getEmail());

            // Log pour vérifier la génération du token
            System.out.println("Token généré : " + token);

            // Retourner la réponse avec le token et les informations de l'utilisateur
            return ResponseEntity.ok(new JwtResponse(
                    token,
                    utilisateur.getRole().getRole(), // Rôle de l'utilisateur
                    utilisateur.getEmail(),
                    utilisateur.getIdUtilisateur()
            ));
        } catch (BadCredentialsException e) {
            // Log pour vérifier les erreurs
            System.out.println("Échec de l'authentification : Email ou mot de passe incorrect");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Email ou mot de passe incorrect"));
        } catch (Exception e) {
            // Log pour vérifier les erreurs
            System.out.println("Erreur d'authentification : " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erreur lors de l'authentification : " + e.getMessage()));
        }
    }


    @PostMapping(value = "/signup/client", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signupClient(@RequestBody ClientSignupRequest clientRequest) {
        try {
            // Afficher les données reçues
            System.out.println("Données reçues :");
            System.out.println("Nom complet : " + clientRequest.getNomComplet());
            System.out.println("Email : " + clientRequest.getEmail());
            System.out.println("Téléphone : " + clientRequest.getTelephone());
            System.out.println("Adresse : " + clientRequest.getAdresse());

            // Vérifier si le nom complet est manquant
            if (clientRequest.getNomComplet() == null || clientRequest.getNomComplet().isEmpty()) {
                System.out.println("Erreur : Le nom complet est manquant.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse("Le nom complet est requis."));
            }

            // Appeler la méthode createClient pour enregistrer l'utilisateur
            utilisateurService.createClient(clientRequest);

            // Retourner une réponse JSON de succès
            return ResponseEntity.ok(new SuccessResponse("Inscription réussie pour " + clientRequest.getEmail()));
        } catch (EmailAlreadyUsedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Erreur lors de l'inscription"));
        }
    }




        @PostMapping(value = "/signup/concessionnaire", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> signupConcessionnaire(
                @RequestPart("concessionnaire") ConcessionnaireSignupRequest concessionnaireRequest,
                @RequestPart("logo") MultipartFile logo) {
            System.out.println("houni ena rani 9bal try:");
            try {
                // Afficher les données reçues
                System.out.println("Données reçues pour l'inscription du concessionnaire :");
                System.out.println("Email : " + concessionnaireRequest.getEmail());
                System.out.println("Nom complet : " + concessionnaireRequest.getNomComplet());
                System.out.println("Téléphone : " + concessionnaireRequest.getTelephone());
                System.out.println("Adresse : " + concessionnaireRequest.getAdresse());
                System.out.println("Site web : " + concessionnaireRequest.getSiteWeb());
                System.out.println("Numéro de taxe : " + concessionnaireRequest.getNumeroTax());
                System.out.println("Licence commerciale : " + concessionnaireRequest.getLicenceCommerciale());
                System.out.println("Description : " + concessionnaireRequest.getDescription());
                System.out.println("Nom du gérant : " + concessionnaireRequest.getNomGerant());
                System.out.println("Email du gérant : " + concessionnaireRequest.getEmailGerant());
                System.out.println("Téléphone du gérant : " + concessionnaireRequest.getTelephoneGerant());
                System.out.println("Pays : " + concessionnaireRequest.getPaye());
                System.out.println("Logo : " + (logo != null ? logo.getOriginalFilename() : "Aucun logo fourni"));

                // Appeler le service pour enregistrer le concessionnaire
                concessionnaireService.createConcessionnaire(concessionnaireRequest, logo);

                // Retourner une réponse JSON de succès
                return ResponseEntity.ok(new SuccessResponse("Inscription réussie pour " + concessionnaireRequest.getEmail() + ". Statut : en attente"));
            } catch (EmailAlreadyUsedException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(e.getMessage()));
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse("Erreur lors de l'inscription"));
            }
        }



    @PostMapping("/send-verification-code")
    public ResponseEntity<?> sendVerificationCode(@RequestParam String email) {
        try {
            utilisateurService.sendVerificationCode(email);
            return ResponseEntity.ok("Code de vérification envoyé à " + email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'envoi du code de vérification");
        }
    }

    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@RequestBody VerificationRequest verificationRequest) {
        try {

            System.out.println("Code reçu pour la vérification(hetha howa l code ) : " + verificationRequest.getCode());
            System.out.println("Début de la vérification du code pour l'email : " + verificationRequest.getEmail());

            // Vérifier si l'utilisateur est un client ou une agence
            if (utilisateurService.isClient(verificationRequest.getEmail())) {
                System.out.println("Activation du client pour l'email : " + verificationRequest.getEmail());
                utilisateurService.activateClient(verificationRequest.getEmail(), verificationRequest.getCode());
            } else {
                System.out.println("Aucun utilisateur trouvé pour l'email : " + verificationRequest.getEmail());
                throw new RuntimeException("Aucun utilisateur trouvé pour cet email.");
            }

            return ResponseEntity.ok().body(Map.of("message", "Code de vérification valide"));
        } catch (Exception e) {
            System.out.println("Erreur lors de la vérification du code : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erreur lors de la vérification du code"));
        }
    }

    @PostMapping("/activate-client")
    public ResponseEntity<?> activateClient(
            @RequestParam String email,
            @RequestParam String code) { // Supprimer clientRequest
        try {
            utilisateurService.activateClient(email, code); // Appeler avec 2 arguments seulement
            return ResponseEntity.ok("Compte activé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }



    @Autowired
    private UtilisateurRepository utilisateurRepsitory;

    @Autowired
    private EmailService emailService; // Injecter le service d'envoi d'email
    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");

        try {
            // Récupérer l'utilisateur par email
            Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                    .orElseThrow(() -> new EmailNotFoundException("Email non trouvé."));

            // Vérifier le statut de l'utilisateur
            if (!utilisateur.getStatut().getStatut().equals("Accepter")) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Cet email n'est pas actif. Veuillez contacter l'administrateur.");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
            }

            // Si le statut est actif, envoyer le code de vérification
            passwordResetService.sendVerificationCode(email);

            // Renvoyer une réponse JSON
            Map<String, String> response = new HashMap<>();
            response.put("message", "Un code de vérification a été envoyé à votre email.");
            return ResponseEntity.ok(response);
        } catch (EmailNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erreur lors de l'envoi du code de vérification.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/verify-code-password")
    public ResponseEntity<Map<String, String>> verifyCode(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String code = body.get("code");

        try {
            // Vérifier le code de vérification
            if (!passwordResetService.verifyCode(email, code)) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("message", "Code de vérification invalide ou expiré.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Code de vérification valide.");
            return ResponseEntity.ok(successResponse);
        } catch (EmailNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erreur lors de la vérification du code.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @PostMapping("/update-password")
    public ResponseEntity<Map<String, String>> updatePassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String newPassword = body.get("newPassword");

        try {
            // Mettre à jour le mot de passe
            passwordResetService.updatePassword(email, newPassword);

            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", "Mot de passe mis à jour avec succès.");
            return ResponseEntity.ok(successResponse);
        } catch (EmailNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erreur lors de la mise à jour du mot de passe.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    /**
     * Génère un code de vérification à 6 chiffres.
     *
     * @return Le code de vérification.
     */
    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999)); // Générer un code à 6 chiffres
    }

    // Endpoint pour récupérer tous les rôles disponibles
    @GetMapping("/roles")
    public ResponseEntity<List<RoleUtilisateur>> getAllRoles() {
        List<RoleUtilisateur> roles = utilisateurService.getAllRoles();
        return ResponseEntity.ok(roles);
    }



}