package org.example.entities;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.List;

public class Patient {
    private UUID id;
    private String numeroSecuriteSociale;
    private String nom;
    private String sexe;
    private LocalDate dateNaissance;
    private String email;
    private double poids;
    private double hauteur;

    private Set<String> listTelephones;
    private Set<String> listPrenoms;
    private Map<String, String> adresse;
    private List<String> allergies;
    private List<Consultation> consultations;
    private List<RendezVous> rendezVous;

    public Patient(){}

    public Patient(UUID id, String numeroSecuriteSociale, String nom, String sexe, LocalDate dateNaissance,
                   String email, double poids, double hauteur, Set<String> listTelephones,
                   Set<String> listPrenoms, Map<String, String> adresse, List<String> allergies) {
        this.id = id;
        this.numeroSecuriteSociale = numeroSecuriteSociale;
        this.nom = nom;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
        this.email = email;
        this.poids = poids;
        this.hauteur = hauteur;
        this.listTelephones = listTelephones;
        this.listPrenoms = listPrenoms;
        this.adresse = adresse;
        this.allergies = allergies;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getNumeroSecuriteSociale() { return numeroSecuriteSociale; }
    public void setNumeroSecuriteSociale(String numeroSecuriteSociale) { this.numeroSecuriteSociale = numeroSecuriteSociale; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getPoids() { return poids; }
    public void setPoids(double poids) { this.poids = poids; }

    public double getHauteur() { return hauteur; }
    public void setHauteur(double hauteur) { this.hauteur = hauteur; }

    public Set<String> getListTelephones() { return listTelephones; }
    public void setListTelephones(Set<String> listTelephones) { this.listTelephones = listTelephones; }

    public Set<String> getListPrenoms() { return listPrenoms; }
    public void setListPrenoms(Set<String> listPrenoms) { this.listPrenoms = listPrenoms; }

    public Map<String, String> getAdresse() { return adresse; }
    public void setAdresse(Map<String, String> adresse) { this.adresse = adresse; }

    public List<String> getAllergies() { return allergies; }
    public void setAllergies(List<String> allergies) { this.allergies = allergies; }

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
        return "Patient{" +
                "id=" + id +
                ", numeroSecuriteSociale='" + numeroSecuriteSociale + '\'' +
                ", nom='" + nom + '\'' +
                ", sexe='" + sexe + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", email='" + email + '\'' +
                ", poids=" + poids +
                ", hauteur=" + hauteur +
                ", listTelephones=" + listTelephones +
                ", listPrenoms=" + listPrenoms +
                ", adresse=" + adresse +
                ", allergies=" + allergies +
                '}';
    }
}
