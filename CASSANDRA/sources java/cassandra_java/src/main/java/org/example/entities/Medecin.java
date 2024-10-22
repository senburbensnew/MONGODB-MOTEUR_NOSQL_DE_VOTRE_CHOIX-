package org.example.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Medecin {
    private UUID id;
    private String nom;
    private String sexe;
    private LocalDate dateNaissance;
    private String specialite;
    private String email;
    private String cv;
    private Map<String, String> adresse;
    private Set<String> listTelephones;
    private Set<String> listPrenoms;
    private List<Consultation> consultations;
    private List<RendezVous> rendezVous;

    public Medecin(UUID id, String nom, String sexe, LocalDate dateNaissance, String specialite,
                   String email, String cv, Map<String, String> adresse,
                   Set<String> listTelephones, Set<String> listPrenoms) {
        this.id = id;
        this.nom = nom;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
        this.specialite = specialite;
        this.email = email;
        this.cv = cv;
        this.adresse = adresse;
        this.listTelephones = listTelephones;
        this.listPrenoms = listPrenoms;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public Map<String, String> getAdresse() {
        return adresse;
    }

    public void setAdresse(Map<String, String> adresse) {
        this.adresse = adresse;
    }

    public Set<String> getListTelephones() {
        return listTelephones;
    }

    public void setListTelephones(Set<String> listTelephones) {
        this.listTelephones = listTelephones;
    }

    public Set<String> getListPrenoms() {
        return listPrenoms;
    }

    public void setListPrenoms(Set<String> listPrenoms) {
        this.listPrenoms = listPrenoms;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public List<RendezVous> getRendezVous() {
        return rendezVous;
    }

    public void setRendezVous(List<RendezVous> rendezVous) {
        this.rendezVous = rendezVous;
    }

    @Override
    public String toString() {
        return "Medecin{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", sexe='" + sexe + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", specialite='" + specialite + '\'' +
                ", email='" + email + '\'' +
                ", cv='" + cv + '\'' +
                ", adresse=" + adresse +
                ", listTelephones=" + listTelephones +
                ", listPrenoms=" + listPrenoms +
                '}';
    }
}