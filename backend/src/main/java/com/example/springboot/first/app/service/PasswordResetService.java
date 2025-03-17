package com.example.springboot.first.app.service;

import com.example.springboot.first.app.exception.EmailNotFoundException;
import com.example.springboot.first.app.model.Utilisateur;
import com.example.springboot.first.app.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class PasswordResetService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService; // Injecter EmailService

    private final Map<String, VerificationData> verificationData = new HashMap<>();

    private static class VerificationData {
        String code;
        long timestamp;

        VerificationData(String code, long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
        }
    }

    // Méthode pour envoyer un code de vérification
    public void sendVerificationCode(String email) throws EmailNotFoundException {
        // Vérifier si l'email existe dans la base de données
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Email non trouvé : " + email));

        // Générer un code de vérification
        String verificationCode = generateVerificationCode();
        System.out.println("Code de vérification généré : " + verificationCode);

        // Stocker le code de vérification temporairement
        verificationData.put(email, new VerificationData(verificationCode, System.currentTimeMillis()));

        // Envoyer le code de vérification par email
        emailService.sendVerificationEmail(email, verificationCode);
        System.out.println("Email de vérification envoyé à : " + email);
    }

    // Vérifier le code de vérification
    public boolean verifyCode(String email, String code) {
        VerificationData data = verificationData.get(email);
        if (data == null || !data.code.equals(code)) {
            return false; // Code invalide
        }

        // Vérifier l'expiration du code (par exemple, 10 minutes)
        long currentTime = System.currentTimeMillis();
        long expirationTime = data.timestamp + (10 * 60 * 1000); // 10 minutes
        return currentTime <= expirationTime; // Retourne true si le code est valide et non expiré
    }

    // Mettre à jour le mot de passe
    public void updatePassword(String email, String newPassword) throws EmailNotFoundException {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Email non trouvé : " + email));

        // Encoder le nouveau mot de passe
        user.setMotDePasse(passwordEncoder.encode(newPassword));
        utilisateurRepository.save(user);

        // Supprimer le code de vérification après utilisation
        verificationData.remove(email);
    }

    // Générer un code de vérification à 6 chiffres
    private String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999)); // Générer un code à 6 chiffres
    }
}