package org.example.entities;

import java.time.LocalDate;
import java.util.UUID;

public class RendezVous {
    private LocalDate rendezvousDate;
    private UUID patientId;
    private UUID doctorId;
    private String motif;

    public RendezVous(LocalDate rendezvousDate, UUID patientId, UUID doctorId, String motif) {
        this.rendezvousDate = rendezvousDate;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.motif = motif;
    }

    public LocalDate getRendezvousDate() { return rendezvousDate; }
    public void setRendezvousDate(LocalDate rendezvousDate) { this.rendezvousDate = rendezvousDate; }

    public UUID getPatientId() { return patientId; }
    public void setPatientId(UUID patientId) { this.patientId = patientId; }

    public UUID getDoctorId() { return doctorId; }
    public void setDoctorId(UUID doctorId) { this.doctorId = doctorId; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    @Override
    public String toString() {
        return "RendezVous{" +
                "rendezvousDate=" + rendezvousDate +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", motif='" + motif + '\'' +
                '}';
    }
}
