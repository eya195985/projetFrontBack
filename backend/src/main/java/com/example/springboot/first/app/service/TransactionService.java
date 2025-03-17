package com.example.springboot.first.app.service;

import com.example.springboot.first.app.model.*;
import com.example.springboot.first.app.repository.*;
import com.example.springboot.first.app.repository.VolRepository;
import com.example.springboot.first.app.repository.ClasseVolRepository;
import com.example.springboot.first.app.repository.TypeVoyageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private VoyageRepository voyageRepository;

    @Autowired
    private VolRepository volRepository;

    @Autowired
    private ClasseVolRepository classeVolRepository;

    @Autowired
    private TypeVoyageRepository typeVoyageRepository;

    @Autowired
    private PaysRepository paysRepository;

    @Autowired
    private EscaleVolRepository escaleVolRepository;

    @Autowired
    private ItineraireRepository itineraireRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ServiceRepository servicesRepository;


    @Autowired
    private TypeHotelRepository typeHotelRepository;


    @Autowired
    private ServiceSupplementaireRepository serviceSupplementaireRepository;


    @Autowired
    private SupplementairesRepository supplementairesRepository;


    @Autowired
    private TypeTransportRepository typeTransportRepository;

    @Autowired
    private TransportRepository transportRepository;

    @Autowired
    private ServiceTransportRepository serviceTransportRepository;




    @Autowired
    private TypeNourritureRepository typeNourritureRepository;

    @Autowired
    private NourritureRepository nourritureRepository;

    @Autowired
    private ServiceNourritureRepository serviceNourritureRepository;


    @Autowired
    private ItineraireServiceRepository itineraireServiceRepository;

    @Autowired
    private ConcessionnaireRepository concessionnaireRepository;

    @Autowired
    private StatutAnnonceRepository statutAnnonceRepository;

    @Autowired
    private AnnonceRepository annonceRepository;




    @Transactional
    public Voyage saveVoyageData(Map<String, Object> voyageData , Utilisateur utilisateur) {
        try {

            Concessionnaire concessionnaire = concessionnaireRepository.findByUtilisateur(utilisateur)
                    .orElseThrow(() -> new RuntimeException("Concessionnaire non trouvé pour l'utilisateur avec l'ID : "));

            Long ConcessionnaireId= concessionnaire.getIdConcessionnaire();

            // Récupérer le statut "actif"
            StatutAnnonce statut = statutAnnonceRepository.findByStatut("En attente")
                    .orElseThrow(() -> {
                        System.out.println("Statut 'actif' non trouvé.");
                        return new RuntimeException("Statut 'Accepter' non trouvé.");
                    });

            // Log the received voyage data for debugging
            System.out.println("Received Voyage Data: " + voyageData);

            // Convert Map to Voyage object
            Voyage voyage = new Voyage();
            voyage.setTitreVoyage((String) voyageData.get("titreVoyage"));
            voyage.setDescription((String) voyageData.get("description"));
            voyage.setPrix(((Number) voyageData.get("prix")).doubleValue());
            voyage.setDuree(((Number) voyageData.get("duree")).intValue());
            voyage.setIdTypeVoyage(((Number) voyageData.get("idTypeVoyage")).longValue());
            voyage.setCircuit(CircuitEnum.valueOf((String) voyageData.get("circuit")));
            voyage.setTailleGroupe(((Number) voyageData.get("tailleGroupe")).intValue());
            voyage.setTrancheAge((String) voyageData.get("trancheAge"));
            voyage.setGuide(GuideEnum.valueOf((String) voyageData.get("guide")));
            voyage.setLangueGuide((String) voyageData.get("langueGuide"));


            // Convert ageMin and ageMax to trancheAge
            if (voyageData.containsKey("ageMin") && voyageData.containsKey("ageMax")) {
                voyage.setTrancheAge(voyageData.get("ageMin") + "-" + voyageData.get("ageMax"));
            } else {
                throw new RuntimeException("Les champs ageMin et ageMax sont obligatoires.");
            }

            // Ensure idTypeVoyage is set and valid
            if (voyage.getIdTypeVoyage() == null) {
                throw new RuntimeException("L'ID du type de voyage est obligatoire.");
            }

            Optional<TypeVoyage> typeVoyageOptional = typeVoyageRepository.findById(voyage.getIdTypeVoyage());
            if (typeVoyageOptional.isPresent()) {
                voyage.setTypeVoyage(typeVoyageOptional.get());
            } else {
                throw new RuntimeException("TypeVoyage non trouvé avec l'ID : " + voyage.getIdTypeVoyage());
            }

            // Save associated Vol
            if (voyageData.containsKey("vol")) {
                Map<String, Object> volData = (Map<String, Object>) voyageData.get("vol");
                Vol vol = new Vol();
                vol.setNumeroVol((String) volData.get("numeroVol"));
                vol.setNomCompagnieAerienne((String) volData.get("nomCompagnieAerienne"));
                vol.setPaysDepart((String) volData.get("paysDepart"));
                vol.setPaysArrivee((String) volData.get("paysArrivee"));
                vol.setDateDepart((String) volData.get("dateDepart")); // Stocker en tant que String
                vol.setDateArrivee((String) volData.get("dateArrivee")); // Stocker en tant que String
                System.out.println("class vol id ahawa : " + volData.get("classeVolId"));
                vol.setClasseVolId((int) volData.get("classeVolId"));
                vol.setEscale(EscaleEnum.valueOf((String) volData.get("escale")));

                // Save Vol
                vol = volRepository.save(vol);
                voyage.setVol(vol);

                // Save associated escales
                if (volData.containsKey("escales_aller")) {
                    List<?> escalesAllerRaw = (List<?>) volData.get("escales_aller");

                    // Convertir chaque élément en Long
                    List<Long> escalesAller = new ArrayList<>();
                    for (Object escale : escalesAllerRaw) {
                        if (escale instanceof Integer) {
                            escalesAller.add(((Integer) escale).longValue()); // Convertir Integer en Long
                        } else if (escale instanceof Long) {
                            escalesAller.add((Long) escale); // Déjà un Long
                        } else if (escale instanceof String) {
                            escalesAller.add(Long.parseLong((String) escale)); // Convertir String en Long
                        } else {
                            throw new RuntimeException("Type non supporté pour escale : " + escale.getClass());
                        }
                    }

                    // Traiter chaque escale
                    for (Long paysId : escalesAller) {
                        // Vérifier si le pays existe
                        Optional<Pays> paysOptional = paysRepository.findById(paysId);
                        if (paysOptional.isPresent()) {
                            Pays pays = paysOptional.get();

                            // Créer une nouvelle entité EscaleVol
                            EscaleVol escale = new EscaleVol();
                            EscaleVolId escaleId = new EscaleVolId(vol.getIdVol(), pays.getIdPaysDestination());
                            escale.setId(escaleId);
                            escale.setVol(vol); // Associer le vol actuel
                            escale.setPaysDestination(pays); // Associer le pays de destination

                            // Sauvegarder l'escale dans la base de données
                            escaleVolRepository.save(escale);
                        } else {
                            throw new RuntimeException("Pays non trouvé avec l'ID : " + paysId);
                        }
                    }
                }
            }

            // Save Voyage
            Voyage savedVoyage = voyageRepository.save(voyage);
            // --------------Save annonce ----------------//
            Long voyageId = savedVoyage.getIdVoyage();
            Annonce annonce = new Annonce();
            annonce.setConcessionnaire(concessionnaire);
            annonce.setVoyage(voyage);
            annonce.setStatutAnnonce(statut);
            Annonce savecAnnonce = annonceRepository.save(annonce);


            // Save associated itineraires
            if (voyageData.containsKey("itineraires")) {
                List<Map<String, Object>> itinerairesData = (List<Map<String, Object>>) voyageData.get("itineraires");
                for (Map<String, Object> itineraireData : itinerairesData) {
                    Itineraire itineraire = new Itineraire();
                    itineraire.setActivites((String) itineraireData.get("activite"));
                    itineraire.setDestinationRegionJour((String) itineraireData.get("destination"));
                    itineraire.setVoyage(savedVoyage);

                    // Save Itineraire
                    itineraire = itineraireRepository.save(itineraire);

                    // Save associated Hotel
                    if (itineraireData.containsKey("hotelType") && itineraireData.containsKey("hotelName")) {
                        Hotel hotel = new Hotel();
                        hotel.setNomHotel((String) itineraireData.get("hotelName"));

                        Integer hotelTypeId = Integer.parseInt((String) itineraireData.get("hotelType"));
                        Optional<TypeHotel> typeHotelOptional = typeHotelRepository.findById(hotelTypeId);

                        if (typeHotelOptional.isPresent()) {
                            hotel.setTypeHotel(typeHotelOptional.get());
                        } else {
                            throw new RuntimeException("TypeHotel non trouvé avec l'ID : " + hotelTypeId);
                        }

                        // Sauvegarder l'hôtel
                        hotel = hotelRepository.save(hotel);

                        // Créer un nouveau service et l'associer à l'hôtel
                        Services service = new Services();
                        service.setHotel(hotel); // Associer l'hôtel au service
                        service.setDescription("Service associé à l'hôtel " + hotel.getNomHotel());

                        // Sauvegarder le service
                        service = servicesRepository.save(service);

                        // Insérer les services supplémentaires
                        if (itineraireData.containsKey("services")) {
                            List<String> autreServices = (List<String>) itineraireData.get("services");
                            System.out.println("Services supplémentaires trouvés : " + autreServices);

                            if (autreServices != null && !autreServices.isEmpty()) {
                                for (String nomService : autreServices) {
                                    System.out.println("Traitement du service : " + nomService);

                                    // Créer un nouveau service supplémentaire
                                    ServiceSupplementaire serviceSupplementaire = new ServiceSupplementaire();
                                    serviceSupplementaire.setNomService(nomService.trim());
                                    serviceSupplementaire.setDescription("Description par défaut pour " + nomService.trim());

                                    // Sauvegarder le service supplémentaire
                                    serviceSupplementaire = serviceSupplementaireRepository.save(serviceSupplementaire);
                                    System.out.println("Service supplémentaire sauvegardé : " + serviceSupplementaire);

                                    // Insérer dans la table de jointure Supplementaires
                                    SupplementairesId supplementairesId = new SupplementairesId();
                                    supplementairesId.setServiceSupplementaireId(serviceSupplementaire.getId());
                                    supplementairesId.setServiceId(service.getId());

                                    Supplementaires supplementaire = new Supplementaires();
                                    supplementaire.setId(supplementairesId);
                                    supplementaire.setServiceSupplementaire(serviceSupplementaire);
                                    supplementaire.setService(service);

                                    // Sauvegarder l'entrée dans la table de jointure
                                    supplementairesRepository.save(supplementaire);
                                    System.out.println("Entrée dans la table de jointure sauvegardée : " + supplementaire);
                                }
                            } else {
                                System.out.println("Aucun service supplémentaire trouvé dans l'itinéraire.");
                            }
                        } else {
                            System.out.println("La clé 'autre_services' est absente dans l'itinéraire.");
                        }
                        if (itineraireData.containsKey("transportIds")) {
                            List<Integer> transportTypeIds = (List<Integer>) itineraireData.get("transportIds");
                            System.out.println("Types de transport trouvés : " + transportTypeIds);

                            if (transportTypeIds != null && !transportTypeIds.isEmpty()) {
                                for (Integer transportTypeId : transportTypeIds) {
                                    System.out.println("Traitement du type de transport avec l'ID : " + transportTypeId);

                                    // Vérifier si le type de transport existe
                                    Optional<TypeTransport> typeTransportOptional = typeTransportRepository.findById(transportTypeId);
                                    if (typeTransportOptional.isPresent()) {
                                        TypeTransport typeTransport = typeTransportOptional.get();

                                        // Créer un nouveau transport associé à ce type de transport
                                        Transport transport = new Transport();
                                        transport.setTypeTransport(typeTransport);
                                        transport.setCompagnie("Compagnie par défaut"); // Vous pouvez personnaliser ce champ

                                        // Sauvegarder le transport
                                        transport = transportRepository.save(transport);
                                        System.out.println("Nouveau transport créé avec l'ID : " + transport.getIdTransport());

                                        // Créer une nouvelle entrée dans la table Service_Transport
                                        ServiceTransportId serviceTransportId = new ServiceTransportId(service.getId(), transport.getIdTransport());
                                        ServiceTransport serviceTransport = new ServiceTransport();
                                        serviceTransport.setId(serviceTransportId);
                                        serviceTransport.setService(service);
                                        serviceTransport.setTransport(transport);

                                        // Sauvegarder l'entrée dans la table Service_Transport
                                        serviceTransportRepository.save(serviceTransport);
                                        System.out.println("Entrée dans la table Service_Transport sauvegardée : " + serviceTransport);
                                    } else {
                                        // Ignorer les types de transport manquants et continuer
                                        System.out.println("Type de transport non trouvé avec l'ID : " + transportTypeId + ". Ignoré.");
                                    }
                                }
                            } else {
                                System.out.println("Aucun type de transport trouvé dans l'itinéraire.");
                            }
                        } else {
                            System.out.println("La clé 'transportIds' est absente dans l'itinéraire.");
                        }

                        // Insérer les nourritures dans la table Nourriture et Service_Nourriture
                        if (itineraireData.containsKey("nourriture")) {
                            List<Integer> nourritureIds = (List<Integer>) itineraireData.get("nourriture");
                            System.out.println("Nourritures trouvées : " + nourritureIds);

                            if (nourritureIds != null && !nourritureIds.isEmpty()) {
                                for (Integer typeNourritureId : nourritureIds) {
                                    System.out.println("Traitement du type de nourriture : " + typeNourritureId);

                                    // Vérifier si le type de nourriture existe
                                    Optional<TypeNourriture> typeNourritureOptional = typeNourritureRepository.findById(typeNourritureId);
                                    if (typeNourritureOptional.isPresent()) {
                                        TypeNourriture typeNourriture = typeNourritureOptional.get();

                                        // Créer une nouvelle nourriture
                                        Nourriture nourriture = new Nourriture();
                                        nourriture.setTypeNourriture(typeNourriture);
                                        nourriture.setOptions("Options par défaut"); // Vous pouvez personnaliser ce champ

                                        // Sauvegarder la nourriture
                                        nourriture = nourritureRepository.save(nourriture);
                                        System.out.println("Nourriture sauvegardée : " + nourriture);

                                        // Créer une nouvelle entrée dans la table Service_Nourriture
                                        ServiceNourritureId serviceNourritureId = new ServiceNourritureId(service.getId(), nourriture.getId());
                                        ServiceNourriture serviceNourriture = new ServiceNourriture();
                                        serviceNourriture.setId(serviceNourritureId);
                                        serviceNourriture.setService(service);
                                        serviceNourriture.setNourriture(nourriture);

                                        // Sauvegarder l'entrée dans la table Service_Nourriture
                                        serviceNourritureRepository.save(serviceNourriture);
                                        System.out.println("Entrée dans la table Service_Nourriture sauvegardée : " + serviceNourriture);
                                    } else {
                                        throw new RuntimeException("TypeNourriture non trouvé avec l'ID : " + typeNourritureId);
                                    }
                                }
                            } else {
                                System.out.println("Aucune nourriture trouvée dans l'itinéraire.");
                            }
                        } else {
                            System.out.println("La clé 'nourriture' est absente dans l'itinéraire.");
                        }


                        ItineraireService itineraireService = new ItineraireService();
                        itineraireService.setItineraire(itineraire); // Associer l'itinéraire déjà créé
                        itineraireService.setService(service); // Associer le service déjà créé
                        itineraireServiceRepository.save(itineraireService);
                    }
                }
            }

            return savedVoyage;
        } catch (Exception e) {
            System.err.println("Erreur lors de la sauvegarde du voyage: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

}