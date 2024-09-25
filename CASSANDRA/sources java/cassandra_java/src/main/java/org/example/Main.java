package org.example;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.example.entities.Medecin;
import org.example.services.MedecinService;
import org.example.services.PatientService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        // Connexion à la base Cassandra
        try (CqlSession session = CqlSession.builder().withKeyspace("cabinet_medical").build()) {
            MedecinService medecinService = new MedecinService(session);
            PatientService patientService = new PatientService(session);

            // Insérer un medecin
            Medecin medecin = new Medecin(
                    Uuids.random(),
                    "Doe",
                    "M",
                    LocalDate.of(1980, 1, 1),
                    "Cardiologie",
                    "doe@example.com",
                    "CV content",
                    Map.of("ville", "Paris", "rue", "Champs-Elysees"),
                    Set.of("123456789", "987654321"),
                    Set.of("John", "Jean")
            );

            medecinService.insertOneMedecin(medecin);

            // Insérer plusieurs medecins
            Medecin medecin1 = new Medecin(
                    Uuids.random(),
                    "other",
                    "F",
                    LocalDate.of(1980, 1, 1),
                    "Cardiologie",
                    "doe@example.com",
                    "CV content",
                    Map.of("ville", "Paris", "rue", "Champs-Elysees"),
                    Set.of("123456789", "987654321"),
                    Set.of("John", "Jean")
            );

            Medecin medecin2 = new Medecin(
                    Uuids.random(),
                    "Smith",
                    "F",
                    LocalDate.of(1975, 5, 15),
                    "Neurologie",
                    "smith@example.com",
                    "CV content",
                    Map.of("ville", "Lyon", "rue", "Rue de la République"),
                    Set.of("012345678", "876543210"),
                    Set.of("Jane", "Marie")
            );
            List<Medecin> medecins = List.of(medecin1, medecin2);
            medecinService.insertMultipleMedecins(medecins);

            Medecin medecinToUpdate = new Medecin(
                    UUID.fromString("e21b23d4-7f5a-4b92-a37f-999e51cbb2e4"),
                    "Doe",
                    "M",
                    LocalDate.of(1980, 1, 1),
                    "Cardiologie",
                    "doe@example.com",
                    "Updated CV path",
                    Map.of("ville", "Paris", "rue", "Updated Champs-Elysees"),
                    Set.of("987654321", "1122334455"),
                    Set.of("John", "Jean", "UpdatedName")
            );

            medecinService.updateOneMedecin(medecinToUpdate);

            UUID id = UUID.fromString("e21b23d4-7f5a-4b92-a37f-999e51cbb2e4");

            medecinService.deleteOneMedecin(
                    "Cardiologie",
                    LocalDate.of(1980, 1, 1),
                    "Doe",
                    "M",
                    "doe@example.com",
                    id
            );
            System.out.println("Medecin deleted successfully.");
        }
    }
}