package com.example.springboot.first.app.service;

import com.example.springboot.first.app.exception.EmailAlreadyUsedException;
import com.example.springboot.first.app.model.*;
import com.example.springboot.first.app.payload.request.ConcessionnaireSignupRequest;
import com.example.springboot.first.app.payload.request.UpdateConcessionnaireRequest;
import com.example.springboot.first.app.payload.response.ConcessionnaireResponse;
import com.example.springboot.first.app.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.nio.ByteBuffer;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConcessionnaireService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ConcessionnaireRepository concessionnaireRepository;

    @Autowired
    private RoleUtilisateurRepository roleUtilisateurRepository;

    @Autowired
    private StatutUtilisateurRepository statutUtilisateurRepository;

    @Autowired
    private UserLogoRepository userLogoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Transactional
    public void createConcessionnaire(ConcessionnaireSignupRequest concessionnaireRequest, MultipartFile logo) {
        System.out.println("==========================================");
        System.out.println("Début de la création du concessionnaire pour l'email : " + concessionnaireRequest.getEmail());
        System.out.println("==========================================");

        // Vérifier si l'email est déjà utilisé
        if (utilisateurRepository.existsByEmail(concessionnaireRequest.getEmail())) {
            System.out.println("Erreur : L'email est déjà utilisé : " + concessionnaireRequest.getEmail());
            throw new EmailAlreadyUsedException("L'email est déjà utilisé. Veuillez en choisir un autre.");
        }

        // Afficher les données reçues
        System.out.println("==========================================");
        System.out.println("Données reçues dans ConcessionnaireSignupRequest :");
        System.out.println("==========================================");
        System.out.println("Nom complet : " + concessionnaireRequest.getNomComplet());
        System.out.println("Email : " + concessionnaireRequest.getEmail());
        System.out.println("Mot de passe : ********"); // Masquer le mot de passe
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

        // Récupérer le rôle "concessionnaire"
        String roleRecherche = "concessionaire".trim().toLowerCase(); // Nettoyer la chaîne
        System.out.println("Rôle recherché : " + roleRecherche);
        RoleUtilisateur roleConcessionnaire = roleUtilisateurRepository.findByRoleIgnoreCase(roleRecherche)
                .orElseThrow(() -> {
                    System.out.println("Rôle '" + roleRecherche + "' non trouvé.");
                    return new RuntimeException("Rôle '" + roleRecherche + "' non trouvé.");
                });

        // Récupérer le statut "en attente"
        StatutUtilisateur statutEnAttente = statutUtilisateurRepository.findByStatut("en attente")
                .orElseThrow(() -> {
                    System.out.println("Statut 'en attente' non trouvé.");
                    return new RuntimeException("Statut 'en attente' non trouvé.");
                });

        // Créer l'utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNomComplet(concessionnaireRequest.getNomComplet());
        utilisateur.setEmail(concessionnaireRequest.getEmail());
        utilisateur.setRole(roleConcessionnaire); // Définir le rôle "concessionnaire"
        utilisateur.setStatut(statutEnAttente); // Définir le statut "en attente"
        utilisateur.setTelephone(concessionnaireRequest.getTelephone());
        utilisateur.setAdresse(concessionnaireRequest.getAdresse());

        // Enregistrer l'utilisateur
        System.out.println("Création de l'utilisateur pour l'email : " + concessionnaireRequest.getEmail());
        utilisateurRepository.save(utilisateur);
        System.out.println("Utilisateur enregistré avec succès. ID utilisateur : " + utilisateur.getIdUtilisateur());

        // Enregistrer le logo dans Cassandra (si fourni)
        if (logo != null && !logo.isEmpty()) {
            try {
                byte[] logoBytes = logo.getBytes();  // Convertir l'image en tableau de bytes
                ByteBuffer byteBuffer = ByteBuffer.wrap(logoBytes);  // Conversion en ByteBuffer

                // Générer un UUID unique pour le logo
                UUID idLogo = UUID.randomUUID();

                // Créer un objet UserLogo avec l'UUID et le logo
                UserLogo userLogo = new UserLogo(idLogo, byteBuffer);

                // Sauvegarder dans la base de données Cassandra
                userLogoRepository.save(userLogo);
                System.out.println("Logo enregistré avec succès avec l'ID : " + idLogo);

                // Associer l'ID du logo à l'utilisateur
                utilisateur.setIdLogo(idLogo.toString());
                utilisateurRepository.save(utilisateur);
                System.out.println("ID du logo associé à l'utilisateur : " + utilisateur.getIdLogo());
            } catch (Exception e) {
                System.out.println("Erreur lors de l'enregistrement du logo : " + e.getMessage());
            }
        }

        // Créer le concessionnaire
        Concessionnaire concessionnaire = new Concessionnaire();
        concessionnaire.setSiteWeb(concessionnaireRequest.getSiteWeb());
        concessionnaire.setNumeroTax(concessionnaireRequest.getNumeroTax());
        concessionnaire.setLicenceCommerciale(concessionnaireRequest.getLicenceCommerciale());
        concessionnaire.setDescription(concessionnaireRequest.getDescription());
        concessionnaire.setNomGerant(concessionnaireRequest.getNomGerant());
        concessionnaire.setEmailGerant(concessionnaireRequest.getEmailGerant());
        concessionnaire.setTelephoneGerant(concessionnaireRequest.getTelephoneGerant());
        concessionnaire.setPaye(concessionnaireRequest.getPaye());
        concessionnaire.setUtilisateur(utilisateur); // Associer l'utilisateur au concessionnaire

        // Enregistrer le concessionnaire
        System.out.println("Création du concessionnaire pour l'utilisateur : " + utilisateur.getIdUtilisateur());
        concessionnaireRepository.save(concessionnaire);
        System.out.println("Concessionnaire enregistré avec succès. ID concessionnaire : " + concessionnaire.getIdConcessionnaire());

        // Pas d'envoi de code de vérification
        System.out.println("==========================================");
        System.out.println("Inscription du concessionnaire terminée. Statut : en attente");
        System.out.println("==========================================");
    }


    // Récupérer la liste des concessionnaires en attente
    public List<ConcessionnaireResponse> getConcessionnairesEnAttente() {
        List<Concessionnaire> concessionnaires = concessionnaireRepository.findByUtilisateur_Statut_Statut("en attente");

        // Convertir les entités en DTO de réponse
        return concessionnaires.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Mapper une entité Concessionnaire vers un DTO de réponse
    private ConcessionnaireResponse convertToDTO(Concessionnaire concessionnaire) {
        ConcessionnaireResponse dto = new ConcessionnaireResponse();
        Utilisateur utilisateur = concessionnaire.getUtilisateur();

        // Mapper les données de l'entité Utilisateur
        dto.setIdUtilisateur(utilisateur.getIdUtilisateur());
        dto.setNomComplet(utilisateur.getNomComplet());
        dto.setEmail(utilisateur.getEmail());
        dto.setTelephone(utilisateur.getTelephone());
        dto.setAdresse(utilisateur.getAdresse());
        dto.setIdLogo(utilisateur.getIdLogo());

        // Mapper les données de l'entité Concessionnaire
        dto.setIdConcessionnaire(concessionnaire.getIdConcessionnaire());
        dto.setSiteWeb(concessionnaire.getSiteWeb());
        dto.setNumeroTax(concessionnaire.getNumeroTax());
        dto.setLicenceCommerciale(concessionnaire.getLicenceCommerciale());
        dto.setDescription(concessionnaire.getDescription());
        dto.setNomGerant(concessionnaire.getNomGerant());
        dto.setEmailGerant(concessionnaire.getEmailGerant());
        dto.setTelephoneGerant(concessionnaire.getTelephoneGerant());
        dto.setPaye(concessionnaire.getPaye());


        // Récupérez le logo
        if (utilisateur.getIdLogo() != null) {
            UserLogo userLogo = userLogoRepository.findById(UUID.fromString(utilisateur.getIdLogo())).orElse(null);
            if (userLogo != null && userLogo.getLogo() != null) {
                byte[] logoBytes = new byte[userLogo.getLogo().remaining()];
                userLogo.getLogo().get(logoBytes);
                dto.setLogo(logoBytes); // Ajoutez le logo à la réponse
            }
        }


        return dto;
    }


    public void updateStatut(String email, String newStatut) {
        // Récupérer le concessionnaire par email
        Concessionnaire concessionnaire = concessionnaireRepository.findByUtilisateurEmail(email)
                .orElseThrow(() -> new RuntimeException("Concessionnaire non trouvé"));

        // Récupérer le statut correspondant
        StatutUtilisateur statut = statutUtilisateurRepository.findByStatut(newStatut)
                .orElseThrow(() -> new RuntimeException("Statut non trouvé"));

        // Mettre à jour le statut du concessionnaire
        concessionnaire.getUtilisateur().setStatut(statut);

        // Si le statut est "Accepter", générer un mot de passe et envoyer un email
        if ("Accepter".equalsIgnoreCase(newStatut)) {
            String nouveauMotDePasse = genererMotDePasseAleatoire();
            concessionnaire.getUtilisateur().setMotDePasse(passwordEncoder.encode(nouveauMotDePasse));
            emailService.sendPasswordEmail(email, nouveauMotDePasse);
        }

        // Sauvegarder les modifications
        concessionnaireRepository.save(concessionnaire);
    }





    private String genererMotDePasseAleatoire() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder motDePasse = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(caracteres.length());
            motDePasse.append(caracteres.charAt(index));
        }
        System.out.println("==========================================" + motDePasse.toString());
        return motDePasse.toString();
    }


    /**
     * Récupère tous les concessionnaires avec leurs informations, y compris l'ID du logo.
     *
     * @return Une liste de ConcessionnaireResponse.
     */

    /*-----------------------------------*/
    public Optional<Concessionnaire> getConcessionnaireById(Long id) {
        System.out.println("Récupération du concessionnaire avec l'ID : {}" + id);
        return concessionnaireRepository.findById(id);
    }

    public void updateConcessionnaireStatus(Long idConcessionnaire, String status) {
        // Récupérer le concessionnaire
        Concessionnaire concessionnaire = concessionnaireRepository.findById(idConcessionnaire).orElse(null);

        if (concessionnaire != null) {
            // Récupérer l'utilisateur associé
            Utilisateur utilisateur = concessionnaire.getUtilisateur();

            if (utilisateur != null) {
                // Récupérer le statut utilisateur
                StatutUtilisateur statutUtilisateur = statutUtilisateurRepository.findByStatut(status).orElse(null);

                if (statutUtilisateur != null) {
                    // Mettre à jour le statut de l'utilisateur
                    utilisateur.setStatut(statutUtilisateur);
                    utilisateurRepository.save(utilisateur);

                    // Remplacement du logger.info par System.out.println
                    System.out.println("Statut du concessionnaire " + idConcessionnaire + " mis à jour avec succès à " + status);
                } else {
                    // Remplacement du logger.warn par System.out.println
                    System.out.println("Statut non trouvé : " + status);
                }
            } else {
                // Remplacement du logger.warn par System.out.println
                System.out.println("Utilisateur associé non trouvé pour le concessionnaire avec l'ID : " + idConcessionnaire);
            }
        } else {
            // Remplacement du logger.warn par System.out.println
            System.out.println("Concessionnaire non trouvé avec l'ID : " + idConcessionnaire);
        }
    }

    public List<Concessionnaire> getAllConcessionnaires() {
        return concessionnaireRepository.findAll();
    }

    public List<Concessionnaire> getConcessionnairesByUtilisateurStatut(String statut) {
        return concessionnaireRepository.findByUtilisateurStatutUtilisateurStatut(statut);
    }

    /*-------------------------------------------------concessioanire detais---------------------*/


    public ConcessionnaireResponse getConcessionnaireDetails(Utilisateur utilisateur) {
        // Récupérer le concessionnaire associé à cet utilisateur
        Optional<Concessionnaire> concessionnaireOptional = concessionnaireRepository.findByUtilisateur(utilisateur);

        if (concessionnaireOptional.isPresent()) {
            Concessionnaire concessionnaire = concessionnaireOptional.get();

            // Construire la réponse
            ConcessionnaireResponse response = new ConcessionnaireResponse();
            response.setIdConcessionnaire(concessionnaire.getIdConcessionnaire());
            response.setIdUtilisateur(utilisateur.getIdUtilisateur());
            response.setNomComplet(utilisateur.getNomComplet());
            response.setEmail(utilisateur.getEmail());
            response.setTelephone(utilisateur.getTelephone());
            response.setAdresse(utilisateur.getAdresse());
            response.setSiteWeb(concessionnaire.getSiteWeb());
            response.setNumeroTax(concessionnaire.getNumeroTax());
            response.setLicenceCommerciale(concessionnaire.getLicenceCommerciale());
            response.setDescription(concessionnaire.getDescription());
            response.setNomGerant(concessionnaire.getNomGerant());
            response.setEmailGerant(concessionnaire.getEmailGerant());
            response.setTelephoneGerant(concessionnaire.getTelephoneGerant());
            response.setPaye(concessionnaire.getPaye());
            response.setStatut(utilisateur.getStatut().getStatut());
            response.setIdLogo(utilisateur.getIdLogo());


            // Récupérez le logo
            if (utilisateur.getIdLogo() != null) {
                UserLogo userLogo = userLogoRepository.findById(UUID.fromString(utilisateur.getIdLogo())).orElse(null);
                if (userLogo != null && userLogo.getLogo() != null) {
                    byte[] logoBytes = new byte[userLogo.getLogo().remaining()];
                    userLogo.getLogo().get(logoBytes);
                    response.setLogo(logoBytes); // Ajoutez le logo à la réponse
                }
            }

            return response;
        }
        return null; // Retourner null si le concessionnaire n'est pas trouvé
    }

    //---------------update concessioanire ------------------//
    @Transactional
    public ConcessionnaireResponse updateConcessionnaire(Long userId, UpdateConcessionnaireRequest updateRequest) {
        try {
            System.out.println("Début de la mise à jour du concessionnaire dans le service...");

            // Fetch the utilisateur
            System.out.println("Récupération de l'utilisateur avec l'ID : " + userId);
            Utilisateur utilisateur = utilisateurRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID : " + userId));
            System.out.println("Utilisateur trouvé : " + utilisateur.getNomComplet());

            // Update utilisateur details
            System.out.println("Mise à jour des informations de l'utilisateur...");
            utilisateur.setNomComplet(updateRequest.getNomComplet());
            utilisateur.setTelephone(updateRequest.getTelephone());
            utilisateur.setAdresse(updateRequest.getAdresse());
            utilisateur.setEmail(updateRequest.getEmail());
            System.out.println("Informations de l'utilisateur mises à jour.");

            // Fetch the concessionnaire
            System.out.println("Récupération du concessionnaire associé à l'utilisateur...");
            Concessionnaire concessionnaire = concessionnaireRepository.findByUtilisateur(utilisateur)
                    .orElseThrow(() -> new RuntimeException("Concessionnaire non trouvé pour l'utilisateur avec l'ID : " + userId));
            System.out.println("Concessionnaire trouvé : " + concessionnaire.getNomGerant());

            // Update concessionnaire details
            System.out.println("Mise à jour des informations du concessionnaire...");
            concessionnaire.setSiteWeb(updateRequest.getSiteWeb());
            concessionnaire.setNumeroTax(updateRequest.getNumeroTax());
            concessionnaire.setLicenceCommerciale(updateRequest.getLicenceCommerciale());
            concessionnaire.setDescription(updateRequest.getDescription());
            concessionnaire.setNomGerant(updateRequest.getNomGerant());
            concessionnaire.setEmailGerant(updateRequest.getEmailGerant());
            concessionnaire.setTelephoneGerant(updateRequest.getTelephoneGerant());
            concessionnaire.setPaye(updateRequest.getPaye());
            System.out.println("Informations du concessionnaire mises à jour.");

            // Handle the logo
            if (updateRequest.getLogo() != null && !updateRequest.getLogo().isEmpty()) {
                System.out.println("Traitement du logo...");

                // Log du contenu et de la longueur du logo
                System.out.println("Contenu du logo (base64) : " + updateRequest.getLogo().substring(0, Math.min(50, updateRequest.getLogo().length())) + "...");
                System.out.println("Longueur du logo (en octets) : " + updateRequest.getLogo().getBytes().length);

                // Delete the old logo if it exists
                if (utilisateur.getIdLogo() != null) {
                    System.out.println("Suppression de l'ancien logo avec l'ID : " + utilisateur.getIdLogo());
                    userLogoRepository.deleteById(UUID.fromString(utilisateur.getIdLogo()));
                    System.out.println("Ancien logo supprimé avec succès.");
                }

                // Save the new logo
                System.out.println("Enregistrement du nouveau logo...");
                byte[] logoBytes = updateRequest.getLogo().getBytes();
                ByteBuffer byteBuffer = ByteBuffer.wrap(logoBytes);
                UUID idLogo = UUID.randomUUID();
                UserLogo userLogo = new UserLogo(idLogo, byteBuffer);

                // Log de l'ID du nouveau logo
                System.out.println("Nouvel ID de logo généré : " + idLogo);

                // Enregistrement du logo dans la base de données
                userLogoRepository.save(userLogo);
                System.out.println("Nouveau logo enregistré avec succès.");

                // Associate the new logo ID with the utilisateur
                utilisateur.setIdLogo(idLogo.toString());
                System.out.println("Nouvel ID de logo associé à l'utilisateur.");

                // Sauvegarder l'utilisateur après l'association du logo
                utilisateurRepository.save(utilisateur);
                System.out.println("Utilisateur sauvegardé avec le nouvel ID de logo.");
            } else {
                System.out.println("Aucun logo fourni dans la requête.");
            }

            // Save the updated entities
            System.out.println("Sauvegarde des modifications dans la base de données...");
            utilisateurRepository.save(utilisateur); // Sauvegarder à nouveau pour s'assurer que tout est à jour
            concessionnaireRepository.save(concessionnaire);
            System.out.println("Modifications sauvegardées avec succès.");

            // Build and return the response
            System.out.println("Construction de la réponse...");
            ConcessionnaireResponse response = new ConcessionnaireResponse();
            response.setIdConcessionnaire(concessionnaire.getIdConcessionnaire());
            response.setIdUtilisateur(utilisateur.getIdUtilisateur());
            response.setNomComplet(utilisateur.getNomComplet());
            response.setEmail(utilisateur.getEmail());
            response.setTelephone(utilisateur.getTelephone());
            response.setAdresse(utilisateur.getAdresse());
            response.setSiteWeb(concessionnaire.getSiteWeb());
            response.setNumeroTax(concessionnaire.getNumeroTax());
            response.setLicenceCommerciale(concessionnaire.getLicenceCommerciale());
            response.setDescription(concessionnaire.getDescription());
            response.setNomGerant(concessionnaire.getNomGerant());
            response.setEmailGerant(concessionnaire.getEmailGerant());
            response.setTelephoneGerant(concessionnaire.getTelephoneGerant());
            response.setPaye(concessionnaire.getPaye());
            response.setStatut(utilisateur.getStatut().getStatut());




            System.out.println("Réponse construite avec succès : " + response);
            return response;
        } catch (Exception e) {
            // Log the error
            System.out.println("Erreur lors de la mise à jour du concessionnaire : " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour du concessionnaire : " + e.getMessage());
        }
    }
}