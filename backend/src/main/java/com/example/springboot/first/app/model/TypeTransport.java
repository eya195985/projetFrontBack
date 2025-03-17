package com.example.springboot.first.app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity(name = "TypeTransport")
public class TypeTransport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTypeTransport;

    private String nomType;

    private BigDecimal fraisTransport;

    // Constructeurs
    public TypeTransport() {
    }

    public TypeTransport(String nomType, BigDecimal fraisTransport) {
        this.nomType = nomType;
        this.fraisTransport = fraisTransport;
    }

    // Getters et Setters
    public Integer getIdTypeTransport() {
        return idTypeTransport;
    }

    public void setIdTypeTransport(Integer idTypeTransport) {
        this.idTypeTransport = idTypeTransport;
    }

    public String getNomType() {
        return nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    public BigDecimal getFraisTransport() {
        return fraisTransport;
    }

    public void setFraisTransport(BigDecimal fraisTransport) {
        this.fraisTransport = fraisTransport;
    }


}