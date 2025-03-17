package com.example.springboot.first.app.service;

import com.example.springboot.first.app.exception.EmailAlreadyUsedException;
import com.example.springboot.first.app.model.Utilisateur;
import com.example.springboot.first.app.model.RoleUtilisateur;
import com.example.springboot.first.app.model.StatutUtilisateur;
import com.example.springboot.first.app.exception.InvalidPasswordException;
import com.example.springboot.first.app.repository.UtilisateurRepository;
import com.example.springboot.first.app.repository.RoleUtilisateurRepository;
import com.example.springboot.first.app.repository.StatutUtilisateurRepository;
import com.example.springboot.first.app.payload.request.ClientSignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService; // Service pour envoyer des emails

    @Autowired
    private RoleUtilisateurRepository roleUtilisateurRepository; // Repository pour les rôles

    @Autowired
    private StatutUtilisateurRepository statutUtilisateurRepository; // Repository pour les statuts

    private Map<String, VerificationData> verificationData = new HashMap<>(); // Stocker temporairement les codes de vérification
    private Map<String, ClientSignupRequest> temporaryClientData = new HashMap<>(); // Stocker temporairement les données du client

    private static class VerificationData {
        String code;
        long timestamp;

        VerificationData(String code, long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
        }
    }

    /**
     * Envoie un code de vérification à l'email fourni.
     *
     * @param email L'email de l'utilisateur.
     */
    public void sendVerificationCode(String email) {
        System.out.println("Début de l'envoi du code de vérification pour l'email : " + email);
        String code = generateVerificationCode();
        System.out.println("Code généré : " + code); // Afficher le code généré
        verificationData.put(email, new VerificationData(code, System.currentTimeMillis()));
        emailService.sendVerificationEmail(email, code); // Envoyer le code par email
        System.out.println("Code de vérification envoyé à l'email : " + email);
    }

    /**
     * Vérifie si le code de vérification est valide.
     *
     * @param email L'email de l'utilisateur.
     * @param code  Le code de vérification.
     * @return true si le code est valide, false sinon.
     */
    public boolean verifyCode(String email, String code) {
        System.out.println("Vérification du code pour l'email : " + email);
        VerificationData data = verificationData.get(email);
        if (data == null) {
            System.out.println("Aucun code de vérification trouvé pour l'email : " + email);
            return false;
        }

        // Afficher le code stocké et le code saisi
        System.out.println("Code stocké : " + data.code);
        System.out.println("Code saisi : " + code);

        if (!data.code.equals(code)) {
            System.out.println("Code de vérification invalide pour l'email : " + email);
            return false;
        }

        // Vérifier l'expiration (par exemple, 10 minutes)
        long currentTime = System.currentTimeMillis();
        long expirationTime = data.timestamp + (30 * 60 * 1000); // 10 minutes
        if (currentTime > expirationTime) {
            System.out.println("Code de vérification expiré pour l'email : " + email);
            return false;
        }

        System.out.println("Code de vérification valide pour l'email : " + email);
        return true;
    }

    /**
     * Génère un code de vérification à 6 chiffres.
     *
     * @return Le code de vérification.
     */
    private String generateVerificationCode() {
        System.out.println("Génération d'un code de vérification à 6 chiffres.");
        return String.format("%06d", new Random().nextInt(999999)); // Générer un code à 6 chiffres
    }

    /**
     * Active le compte client après vérification du code.
     *
     * @param email L'email de l'utilisateur.
     * @param code  Le code de vérification.
     */
    @Transactional
    public void activateClient(String email, String code) {
        System.out.println("Début de l'activation du client pour l'email : " + email);

        // Vérification du code de vérification
        if (!verifyCode(email, code)) {
            System.out.println("Code de vérification invalide ou expiré pour l'email : " + email);
            throw new RuntimeException("Code de vérification invalide ou expiré.");
        }

        // Récupérer les données temporaires
        System.out.println("Récupération des données temporaires pour l'email : " + email);
        ClientSignupRequest clientRequest = temporaryClientData.get(email);
        if (clientRequest == null) {
            System.out.println("Aucune donnée client trouvée pour l'email : " + email);
            throw new RuntimeException("Aucune donnée client trouvée pour cet email.");
        }

        System.out.println("Données client récupérées pour l'email : " + email);
        System.out.println("Détails du client : Nom complet = " + clientRequest.getNomComplet());
        System.out.println("Mot de passe reçu : " + clientRequest.getMotDePasse());

        // Récupérer le rôle "client"
        String roleRecherche = "client".trim().toLowerCase(); // Nettoyer la chaîne
        System.out.println("Rôle recherché : " + roleRecherche);
        RoleUtilisateur roleClient = roleUtilisateurRepository.findByRoleIgnoreCase(roleRecherche)
                .orElseThrow(() -> {
                    System.out.println("Rôle '" + roleRecherche + "' non trouvé.");
                    return new RuntimeException("Rôle '" + roleRecherche + "' non trouvé.");
                });


        System.out.println("Rôle 'client' trouvé. Détails : " + roleClient);

        // Récupérer le statut "actif"
        StatutUtilisateur statutAccepter = statutUtilisateurRepository.findByStatut("Accepter")
                .orElseThrow(() -> {
                    System.out.println("Statut 'actif' non trouvé.");
                    return new RuntimeException("Statut 'Accepter' non trouvé.");
                });

        // Créer l'utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomComplet(clientRequest.getNomComplet()); // Utiliser nom_complet
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(passwordEncoder.encode(clientRequest.getMotDePasse()));
        utilisateur.setRole(roleClient); // Définir le rôle "client"
        utilisateur.setStatut(statutAccepter); // Définir le statut "actif"
        utilisateur.setTelephone(clientRequest.getTelephone()); // Ajouter le téléphone
        utilisateur.setAdresse(clientRequest.getAdresse()); // Ajouter l'adresse

        System.out.println("Création de l'utilisateur pour l'email : " + email);
        utilisateurRepository.save(utilisateur);
        System.out.println("Utilisateur enregistré avec succès. ID utilisateur : " + utilisateur.getIdUtilisateur());

        // Supprimer les données temporaires
        temporaryClientData.remove(email);
        verificationData.remove(email);

        System.out.println("Données temporaires supprimées pour l'email : " + email);
        System.out.println("Client activé avec succès pour l'email : " + email);
    }
    /**
     * Crée un client après vérification du code.
     *
     * @param clientRequest Les données du client.
     */
    public void createClient(ClientSignupRequest clientRequest) {
        System.out.println("==========================================");
        System.out.println("Début de la création du client pour l'email : " + clientRequest.getEmail());
        System.out.println("==========================================");

        // Vérifier si l'email est déjà utilisé
        if (utilisateurRepository.existsByEmail(clientRequest.getEmail())) {
            System.out.println("Erreur : L'email est déjà utilisé : " + clientRequest.getEmail());
            throw new EmailAlreadyUsedException("L'email est déjà utilisé. Veuillez en choisir un autre.");
        }

        // Afficher les données reçues
        System.out.println("==========================================");
        System.out.println("Données reçues dans ClientSignupRequest :");
        System.out.println("==========================================");
        System.out.println("Nom complet : " + clientRequest.getNomComplet());
        System.out.println("Email : " + clientRequest.getEmail());
        System.out.println("Mot de passe : ********"); // Masquer le mot de passe
        System.out.println("Téléphone : " + clientRequest.getTelephone());
        System.out.println("Adresse : " + clientRequest.getAdresse());

        // Stocker les données temporaires
        System.out.println("==========================================");
        System.out.println("Stockage des données temporaires pour l'email : " + clientRequest.getEmail());
        System.out.println("==========================================");
        temporaryClientData.put(clientRequest.getEmail(), clientRequest);

        // Afficher les données stockées
        System.out.println("==========================================");
        System.out.println("Données stockées dans temporaryClientData :");
        System.out.println("==========================================");
        ClientSignupRequest storedData = temporaryClientData.get(clientRequest.getEmail());
        if (storedData != null) {
            System.out.println("Nom complet : " + storedData.getNomComplet());
            System.out.println("Email : " + storedData.getEmail());
            System.out.println("Mot de passe : "+ storedData.getMotDePasse()); // Masquer le mot de passe
            System.out.println("Téléphone : " + storedData.getTelephone());
            System.out.println("Adresse : " + storedData.getAdresse());
        } else {
            System.out.println("Aucune donnée trouvée pour l'email : " + clientRequest.getEmail());
        }

        // Envoyer un code de vérification par email
        sendVerificationCode(clientRequest.getEmail());
        System.out.println("==========================================");
        System.out.println("Code de vérification envoyé pour l'email : " + clientRequest.getEmail());
        System.out.println("==========================================");
    }
    /**
     * Vérifie si un email existe déjà.
     *
     * @param email L'email à vérifier.
     * @return true si l'email existe, false sinon.
     */
    public boolean isEmailExist(String email) {
        System.out.println("Vérification de l'existence de l'email : " + email);
        return utilisateurRepository.existsByEmail(email);
    }

    /**
     * Vérifie si un client existe déjà.
     *
     * @param email L'email du client.
     * @return true si le client existe, false sinon.
     */
    public boolean isClient(String email) {
        return temporaryClientData.containsKey(email);
    }
    // Récupérer tous les rôles disponibles
    public List<RoleUtilisateur> getAllRoles() {
        return roleUtilisateurRepository.findAll();
    }





    public void changePassword(Long utilisateurId, String oldPassword, String newPassword) throws InvalidPasswordException {
        // Récupérer l'utilisateur par son ID
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));

        // Vérifier que l'ancien mot de passe est correct

        if (!passwordEncoder.matches(oldPassword, utilisateur.getMotDePasse())) {
            // Afficher des messages de débogage
            System.out.println("Ancien mot de passe saisi : " + oldPassword);
            System.out.println("Mot de passe stocké (encodé) : " + utilisateur.getMotDePasse());
            System.out.println("Résultat de la comparaison : " + passwordEncoder.matches(oldPassword, utilisateur.getMotDePasse()));


            // Lever une exception
            throw new InvalidPasswordException("Ancien mot de passe incorrect.");
        }

        // Mettre à jour le nouveau mot de passe
        utilisateur.setMotDePasse(passwordEncoder.encode(newPassword));
        utilisateurRepository.save(utilisateur);
    }
    /*-------------------------------*/


    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public List<Utilisateur> getAllClients() {
        return utilisateurRepository.findByRole("client");
    }

    public Optional<Utilisateur> findById(Long id) {
        return utilisateurRepository.findByIdWithStatut(id);
    }


}