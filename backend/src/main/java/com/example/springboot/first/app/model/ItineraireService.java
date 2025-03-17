package com.example.springboot.first.app.model;
import jakarta.persistence.*;


@Entity
@Table(name = "Itinéraire_Service")
public class ItineraireService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_itinéraire_service")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_itinéraire", nullable = false)
    private Itineraire itineraire;

    @ManyToOne
    @JoinColumn(name = "id_service", nullable = false)
    private Services services;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Itineraire getItineraire() {
        return itineraire;
    }

    public void setItineraire(Itineraire itineraire) {
        this.itineraire = itineraire;
    }

    public Services getService() {
        return services;
    }

    public void setService(Services services) {
        this.services = services;
    }
}
