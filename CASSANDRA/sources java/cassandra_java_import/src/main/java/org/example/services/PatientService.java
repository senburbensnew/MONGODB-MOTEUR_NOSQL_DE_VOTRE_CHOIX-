package org.example.services;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.example.entities.Patient;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class PatientService {
    private CqlSession session;

    public PatientService(CqlSession session) {
        this.session = session;
    }

    public void insertMultiplePatients(List<Patient> patients) {
        String query = "INSERT INTO Patient_By_BirthDay (id, numero_securite_sociale, nom, sexe, date_naissance, email, poids, hauteur, list_telephones, list_prenoms, adresse, allergies) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = session.prepare(query);

        for (Patient patient : patients) {
            BoundStatement boundStatement = preparedStatement.bind(
                    patient.getId(),
                    patient.getNumeroSecuriteSociale(),
                    patient.getNom(),
                    patient.getSexe(),
                    patient.getDateNaissance(),
                    patient.getEmail(),
                    patient.getPoids(),
                    patient.getHauteur(),
                    patient.getListTelephones(),
                    patient.getListPrenoms(),
                    patient.getAdresse(),
                    patient.getAllergies()
            );

            session.execute(boundStatement);
        }
    }

    private Map<String, String> parseAdresse(String adresseStr) {
        return Map.of();
    }

    public void loadDataFromCSV(String csvFilePath){
        List<Patient> patients = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                UUID id = UUID.randomUUID();
                String numeroSecuriteSociale = nextLine[1].trim();
                String nom = nextLine[2].trim();
                String sexe = nextLine[3].trim();
                LocalDate dateNaissance = LocalDate.parse(nextLine[4].trim());
                String email = nextLine[5].trim();
                double poids = Double.parseDouble(nextLine[6].trim());
                double hauteur = Double.parseDouble(nextLine[7].trim());
                Set<String> listTelephones = Set.of(nextLine[8].trim().split(";"));
                Set<String> listPrenoms = Set.of(nextLine[9].trim().split(";"));
                Map<String, String> adresse = Map.of("rue", nextLine[10].trim(), "ville", nextLine[10].trim(), "code_postal", nextLine[11].trim());
                List<String> allergies = List.of(nextLine[11].trim().split(";"));

                Patient patient = new Patient(id, numeroSecuriteSociale, nom, sexe, dateNaissance,
                        email, poids, hauteur, listTelephones, listPrenoms, adresse, allergies);
                patients.add(patient);
            }

            insertMultiplePatients(patients);

        } catch (IOException | CsvException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }
}
