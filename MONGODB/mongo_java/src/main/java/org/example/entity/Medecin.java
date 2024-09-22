package org.example.entity;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

public class Medecin {
    private ObjectId _id;
    private String nom;
    private String sexe;
    private Date dateNaissance;
    private String specialite;
    private String email;
    private String cv;
    private List<String> listTelephones;
    private List<String> listPrenoms;
    private Adresse adresse;
    private List<RendezVous> listRendezVous;
    private List<Consultation> listConsultations;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId id) {
        this._id = id;
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

    public String getCv(){
        return this.cv;
    }

    public void setCv(String cv){
        this.cv = cv;
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

    public String toString(){
        return "Medecin => nom : " + this.nom + ", sexe : " + this.sexe + ", specialite : " + this.specialite + ", email : " + this.email;
    }
}