package org.example.entity;

import java.util.Date;
import java.util.List;

public class Consultation {
    private Medecin medecin;
    private Patient patient;
    private String raison;
    private String diagnostic;
    private Date dateConsultation;
    private List<Examen> listExamens;
    private List<Prescription> listPrescriptions;
    private Facture facture;

    public Medecin getMedecin() {
        return medecin;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getRaison() {
        return raison;
    }

    public void setRaison(String raison) {
        this.raison = raison;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public Date getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(Date dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public List<Examen> getListExamens() {
        return listExamens;
    }

    public void setListExamens(List<Examen> listExamens) {
        this.listExamens = listExamens;
    }

    public List<Prescription> getListPrescriptions() {
        return listPrescriptions;
    }

    public void setListPrescriptions(List<Prescription> listPrescriptions) {
        this.listPrescriptions = listPrescriptions;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }
}