package com.example.springboot.first.app.model;


import jakarta.persistence.*;


@Entity
@Table(name = "Nourriture")
public class Nourriture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nourriture")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_type_nourriture", nullable = false)
    private TypeNourriture typeNourriture;

    @Column(name = "options")
    private String options;

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeNourriture getTypeNourriture() {
        return typeNourriture;
    }

    public void setTypeNourriture(TypeNourriture typeNourriture) {
        this.typeNourriture = typeNourriture;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
}