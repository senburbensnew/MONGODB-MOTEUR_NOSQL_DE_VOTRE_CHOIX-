package org.example;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.example.entities.Medecin;
import org.example.entities.Patient;
import org.example.services.MedecinService;
import org.example.services.PatientService;
import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Connexion à la base Cassandra
        try (CqlSession session = CqlSession.builder().withKeyspace("cabinet_medical").build()) {
            MedecinService medecinService = new MedecinService(session);
            PatientService patientService = new PatientService(session);

            UUID id = Uuids.random();
            // Insérer un medecin
            Medecin medecin = new Medecin(
                    id,
                    "Rubens",
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

            // Recuperer un medecin
            Medecin insertedMedecin = medecinService.getOneMedecin("Cardiologie", LocalDate.of(1980, 1, 1), "Rubens");
            System.out.println(medecin.toString());

            // Insérer plusieurs medecins
            Medecin medecin1 = new Medecin(
                    Uuids.random(),
                    "Etienne",
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

            // Récupérer tous les médecins de la spécialité 'Cardiologie'
            List<Medecin> cardiologues = medecinService.getMedecins("Cardiologie");
            for (Medecin med : cardiologues) {
                System.out.println(medecin);
            }

            // Modifier un medecin
            Medecin medecinToUpdate = new Medecin(
                    UUID.fromString(id.toString()),
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

            // Suppression d'un medecin
            medecinService.deleteOneMedecin(
                    "Cardiologie",
                    LocalDate.of(1980, 1, 1),
                    "Doe",
                    "M",
                    "doe@example.com",
                    id
            );

            // Supprimer plusieurs medecins
            medecinService.deleteMedecins("Neurologie");

            // Creation d'indexes
            medecinService.createIndexes();

            // Groupement
            Long count = medecinService.countRecords("Cardiologie");
            System.out.println(count);

            // Insere un patient
            UUID idPatient = Uuids.random();

            Patient patient = new Patient();
            patient.setId(idPatient);
            patient.setNumeroSecuriteSociale("123456789");
            patient.setNom("John Doe");
            patient.setSexe("M");
            patient.setDateNaissance(LocalDate.of(1985, 5, 12)); // Date of birth (12/05/1985)
            patient.setEmail("johndoe@example.com");
            patient.setPoids(75.5);
            patient.setHauteur(1.80);

            patientService.insertOnePatient(patient);

            // Recuperer un patient
            Patient insertedPatient = patientService.getOnePatient(LocalDate.of(1985, 5, 12));
            System.out.println(medecin.toString());

            // Inserer plusieurs patients
            List<Patient> patients = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Patient newPatient = new Patient();
                newPatient.setId(Uuids.random());
                newPatient.setNumeroSecuriteSociale("123456789" + i);
                newPatient.setNom("Patient " + i);
                newPatient.setSexe(i % 2 == 0 ? "M" : "F");
                newPatient.setDateNaissance(LocalDate.now());
                newPatient.setEmail("patient" + i + "@example.com");
                newPatient.setPoids(70 + i);
                newPatient.setHauteur(170 + i);
                patients.add(patient);
            }
            patientService.insertMultiplePatients(patients);

            // Recuperer plusieurs patients
            patientService.getAllPatients();

            // Modifier un patient
            patient.setNom("Autre nom");
            patientService.updateOnePatient(patient);

            // Supprimer un patient
            patientService.deleteOnePatient(patient);

            // Creation d'indexes
            patientService.createIndexes();

            // Groupement
            Long patientsCount = patientService.countRecords();
            System.out.println(patientsCount);
        }
    }
}