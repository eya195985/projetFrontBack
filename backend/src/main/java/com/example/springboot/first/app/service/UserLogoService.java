package com.example.springboot.first.app.service;

import com.example.springboot.first.app.model.UserLogo;
import com.example.springboot.first.app.repository.UserLogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.ByteBuffer;
import java.util.UUID;

@Service
public class UserLogoService {

    @Autowired
    private UserLogoRepository userLogoRepository; // Repository pour enregistrer le logo

    public void saveLogo(MultipartFile logo) {
        try {
            if (logo != null && !logo.isEmpty()) {
                byte[] logoBytes = logo.getBytes();  // Convertir l'image en tableau de bytes
                ByteBuffer byteBuffer = ByteBuffer.wrap(logoBytes);  // Conversion en ByteBuffer

                // Générer un UUID unique pour le logo
                UUID idLogo = UUID.randomUUID();

                // Créer un objet UserLogo avec l'UUID et le logo
                UserLogo userLogo = new UserLogo(idLogo, byteBuffer);

                // Sauvegarder dans la base de données
                userLogoRepository.save(userLogo);
                System.out.println("Le logo a été enregistré avec succès avec l'ID : " + idLogo);
            } else {
                System.out.println("Aucun logo n'a été reçu ou le fichier est vide.");
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'enregistrement du logo : " + e.getMessage());
        }
    }

    public UserLogo getLogoById(UUID idLogo) {
        return userLogoRepository.findById(idLogo).orElse(null);
    }
}