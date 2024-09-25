package org.example.entities;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Consultation {
    private LocalDate consultationDate;
    private UUID patientId;
    private UUID doctorId;
    private String raison;
    private String diagnostic;
    private Map<String, String> facture;
    private List<Map<String, String>> prescriptions;
    private List<Map<String, String>> examens;

    public Consultation(LocalDate consultationDate, UUID patientId, UUID doctorId, String raison, String diagnostic,
                        Map<String, String> facture, List<Map<String, String>> prescriptions, List<Map<String, String>> examens) {
        this.consultationDate = consultationDate;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.raison = raison;
        this.diagnostic = diagnostic;
        this.facture = facture;
        this.prescriptions = prescriptions;
        this.examens = examens;
    }

    public LocalDate getConsultationDate() { return consultationDate; }
    public void setConsultationDate(LocalDate consultationDate) { this.consultationDate = consultationDate; }

    public UUID getPatientId() { return patientId; }
    public void setPatientId(UUID patientId) { this.patientId = patientId; }

    public UUID getDoctorId() { return doctorId; }
    public void setDoctorId(UUID doctorId) { this.doctorId = doctorId; }

    public String getRaison() { return raison; }
    public void setRaison(String raison) { this.raison = raison; }

    public String getDiagnostic() { return diagnostic; }
    public void setDiagnostic(String diagnostic) { this.diagnostic = diagnostic; }

    public Map<String, String> getFacture() { return facture; }
    public void setFacture(Map<String, String> facture) { this.facture = facture; }

    public List<Map<String, String>> getPrescriptions() { return prescriptions; }
    public void setPrescriptions(List<Map<String, String>> prescriptions) { this.prescriptions = prescriptions; }

    public List<Map<String, String>> getExamens() { return examens; }
    public void setExamens(List<Map<String, String>> examens) { this.examens = examens; }

    @Override
    public String toString() {
        return "Consultation{" +
                "consultationDate=" + consultationDate +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", raison='" + raison + '\'' +
                ", diagnostic='" + diagnostic + '\'' +
                ", facture=" + facture +
                ", prescriptions=" + prescriptions +
                ", examens=" + examens +
                '}';
    }
}