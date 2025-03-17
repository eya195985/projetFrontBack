package com.example.springboot.first.app.service;

import com.example.springboot.first.app.model.Concessionnaire;
import com.example.springboot.first.app.model.StatutUtilisateur;
import com.example.springboot.first.app.model.Utilisateur;
import com.example.springboot.first.app.payload.request.UpdateClientRequest;
import com.example.springboot.first.app.payload.response.ClientResponse;
import com.example.springboot.first.app.repository.StatutUtilisateurRepository;
import com.example.springboot.first.app.repository.UserLogoRepository;
import com.example.springboot.first.app.repository.UtilisateurRepository;
import com.example.springboot.first.app.security.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private UserLogoRepository userLogoRepository;

    @Autowired
    private StatutUtilisateurRepository statutUtilisateurRepository; // Repository pour les statuts

    /**
     * Récupère les détails du client à partir de l'ID de l'utilisateur.
     *
     * @param utilisateurId L'ID de l'utilisateur.
     * @return Un objet ClientResponse contenant les détails du client.
     */
    public ClientResponse getClientDetails(Long utilisateurId) {
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(utilisateurId);

        if (utilisateurOptional.isPresent()) {
            Utilisateur utilisateur = utilisateurOptional.get();

            // Créer une réponse ClientResponse
            ClientResponse response = new ClientResponse();
            response.setIdUtilisateur(utilisateur.getIdUtilisateur());
            response.setNomComplet(utilisateur.getNomComplet());
            response.setEmail(utilisateur.getEmail());
            response.setTelephone(utilisateur.getTelephone());
            response.setAdresse(utilisateur.getAdresse());
            response.setIdLogo(utilisateur.getIdLogo());

            // Récupérer le logo
            if (utilisateur.getIdLogo() != null) {
                userLogoRepository.findById(UUID.fromString(utilisateur.getIdLogo())).ifPresent(userLogo -> {
                    byte[] logoBytes = new byte[userLogo.getLogo().remaining()];
                    userLogo.getLogo().get(logoBytes);
                    response.setLogo(logoBytes);
                });
            }

            return response;
        }
        return null; // Retourner null si l'utilisateur n'est pas trouvé
    }
    @Transactional
    public String updateClientDetails(Long userId, UpdateClientRequest updateRequest) {
        // Vérifier si l'utilisateur existe
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(userId);
        if (utilisateurOptional.isEmpty()) {
            return "Utilisateur non trouvé.";
        }

        Utilisateur utilisateur = utilisateurOptional.get();

        // Mettre à jour les informations de l'utilisateur
        utilisateur.setNomComplet(updateRequest.getNomComplet());
        utilisateur.setTelephone(updateRequest.getTelephone());
        utilisateur.setAdresse(updateRequest.getAdresse());
        utilisateur.setEmail(updateRequest.getEmail());

        // Enregistrer les modifications dans la base de données
        utilisateurRepository.save(utilisateur);

        return "Informations mises à jour avec succès.";
    }


    // update statut pour le client

    public void updateStatut(Long userId, String newStatut) {
        // Récupérer le concessionnaire par email
        Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(userId);


        Utilisateur utilisateur = utilisateurOptional.get();

        // Récupérer le statut correspondant
        StatutUtilisateur statut = statutUtilisateurRepository.findByStatut(newStatut)
                .orElseThrow(() -> new RuntimeException("Statut non trouvé"));

        // Mettre à jour le statut du concessionnaire
         utilisateur.setStatut(statut);

        // Sauvegarder les modifications
        utilisateurRepository.save(utilisateur);
    }

}