package com.example.demo.model;

import java.time.LocalDate;

public class Employee {
    private int id;
    private String nomComplet;
    private String poste;
    private String departement;
    private String email;
    private double salaire;
    private LocalDate dateEmbauche;
    private LocalDate dateFinContrat;
    private String typeContrat;

    public Employee(int id, String nomComplet, String poste, String departement, String email, double salaire, LocalDate dateEmbauche, LocalDate dateFinContrat, String typeContrat) {
        this.id = id;
        this.nomComplet = nomComplet;
        this.poste = poste;
        this.departement = departement;
        this.email = email;
        this.salaire = salaire;
        this.dateEmbauche = dateEmbauche;
        this.dateFinContrat = dateFinContrat;
        this.typeContrat = typeContrat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public LocalDate getDateFinContrat() {
        return dateFinContrat;
    }

    public void setDateFinContrat(LocalDate dateFinContrat) {
        this.dateFinContrat = dateFinContrat;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }
}
