package org.example.entity;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class Patient {
    private ObjectId _id;
    private String numeroSecuriteSociale;
    private String nom;
    private String sexe;
    private Date dateNaissance;
    private String email;
    private double poids;
    private double hauteur;
    private List<String> listTelephones;
    private List<String> listPrenoms;
    private Adresse adresse;
    private List<RendezVous> listRendezVous;
    private List<Consultation> listConsultations;
    private List<AntecedentMedical> antecedentsMedicaux;
    private List<String> allergies;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
    }

    public String getNumeroSecuriteSociale() {
        return numeroSecuriteSociale;
    }

    public void setNumeroSecuriteSociale(String numeroSecuriteSociale) {
        this.numeroSecuriteSociale = numeroSecuriteSociale;
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

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setHauteur(double hauteur) {
        this.hauteur = hauteur;
    }

    public List<String> getListTelephones() {
        return listTelephones;
    }

    public void setListTelephones(List<String> listTelephones) {
        this.listTelephones = listTelephones;
    }

    public List<String> getListPrenoms() {
        return listPrenoms;
    }

    public void setListPrenoms(List<String> listPrenoms) {
        this.listPrenoms = listPrenoms;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public List<RendezVous> getListRendezVous() {
        return listRendezVous;
    }

    public void setListRendezVous(List<RendezVous> listRendezVous) {
        this.listRendezVous = listRendezVous;
    }

    public List<Consultation> getListConsultations() {
        return listConsultations;
    }

    public void setListConsultations(List<Consultation> listConsultations) {
        this.listConsultations = listConsultations;
    }

    public List<AntecedentMedical> getAntecedentsMedicaux() {
        return antecedentsMedicaux;
    }

    public void setAntecedentsMedicaux(List<AntecedentMedical> antecedentsMedicaux) {
        this.antecedentsMedicaux = antecedentsMedicaux;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public String toString(){
        return "nom : " + this.nom + " sexe : " + this.sexe + " email : " + this.email;
    }
}
