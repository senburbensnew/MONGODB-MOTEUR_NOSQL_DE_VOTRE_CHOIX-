package org.example.services;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.example.entities.Medecin;
import org.example.entities.Patient;

import java.io.BufferedReader;
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

   /* public void loadDataFromCSV(String csvFilePath) {
        List<Patient> patients = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(","); // Assuming CSV is comma-separated

                // Parse the values into a Patient object
                UUID id = UUID.randomUUID(); // Generate a new UUID for the patient
                String numeroSecuriteSociale = values[1].trim();
                String nom = values[2].trim();
                String sexe = values[3].trim();
                LocalDate dateNaissance = LocalDate.parse(values[4].trim()); // Adjust format as needed
                String email = values[5].trim();
                double poids = Double.parseDouble(values[6].trim());
                double hauteur = Double.parseDouble(values[7].trim());
                Set<String> listTelephones = Set.of(values[8].trim().split(";")); // Assuming multiple telephones are separated by semicolons
                Set<String> listPrenoms = Set.of(values[9].trim().split(";")); // Assuming multiple prenoms are separated by semicolons
                Map<String, String> adresse = Map.of("rue", values[10].trim(), "ville", values[10].trim(), "code_postal", values[11].trim());
                List<String> allergies = List.of(values[11].trim().split(";")); // Assuming multiple allergies are separated by semicolons

                // Create a new Patient object
                Patient patient = new Patient(id, numeroSecuriteSociale, nom, sexe, dateNaissance,
                        email, poids, hauteur, listTelephones, listPrenoms, adresse, allergies);
                patients.add(patient);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing CSV file: " + e.getMessage());
        }

        // Insert all patients into the database
        insertMultiplePatients(patients);
    }*/

    public void loadDataFromCSV(String csvFilePath){
        List<Patient> patients = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                // Assuming the CSV columns are in the following order:
                // id, nom, sexe, date_naissance, specialite, email, cv, adresse, list_telephones, list_prenoms
                UUID id = UUID.randomUUID(); // Generate a new UUID for the patient
                String numeroSecuriteSociale = nextLine[1].trim();
                String nom = nextLine[2].trim();
                String sexe = nextLine[3].trim();
                LocalDate dateNaissance = LocalDate.parse(nextLine[4].trim()); // Adjust format as needed
                String email = nextLine[5].trim();
                double poids = Double.parseDouble(nextLine[6].trim());
                double hauteur = Double.parseDouble(nextLine[7].trim());
                Set<String> listTelephones = Set.of(nextLine[8].trim().split(";")); // Assuming multiple telephones are separated by semicolons
                Set<String> listPrenoms = Set.of(nextLine[9].trim().split(";")); // Assuming multiple prenoms are separated by semicolons
                Map<String, String> adresse = Map.of("rue", nextLine[10].trim(), "ville", nextLine[10].trim(), "code_postal", nextLine[11].trim());
                List<String> allergies = List.of(nextLine[11].trim().split(";")); // Assuming multiple allergies are separated by semicolons


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
