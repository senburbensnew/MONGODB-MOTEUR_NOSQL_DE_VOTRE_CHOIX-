package org.example;

import com.datastax.oss.driver.api.core.CqlSession;
import org.example.services.MedecinService;
import org.example.services.PatientService;

public class Main {
    public static void main(String[] args) {
        try (CqlSession session = CqlSession.builder().withKeyspace("cabinet_medical").build()) {
            MedecinService medecinService = new MedecinService(session);
            PatientService patientService = new PatientService(session);

            // Load data
            medecinService.loadDataFromCSV("Medecin_By_Speciality.csv");
            patientService.loadDataFromCSV("Patient_By_BirthDay.csv");
        }
    }
}