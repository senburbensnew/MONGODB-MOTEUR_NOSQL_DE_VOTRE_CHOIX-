package org.example.services;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.example.entities.Medecin;

public class MedecinService {
    private CqlSession session;

    public MedecinService(CqlSession session) {
        this.session = session;
    }

    public void insertMultipleMedecins(List<Medecin> medecins) {
        String query = "INSERT INTO Medecin_By_Speciality (id, nom, sexe, date_naissance, specialite, email, cv, adresse, list_telephones, list_prenoms) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = session.prepare(query);

        for (Medecin medecin : medecins) {
            BoundStatement boundStatement = preparedStatement.bind(
                    medecin.getId(),
                    medecin.getNom(),
                    medecin.getSexe(),
                    medecin.getDateNaissance(),
                    medecin.getSpecialite(),
                    medecin.getEmail(),
                    medecin.getCv(),
                    medecin.getAdresse(),
                    medecin.getListTelephones(),
                    medecin.getListPrenoms()
            );

            session.execute(boundStatement);
        }
    }

    public void loadDataFromCSV(String csvFilePath){
        List<Medecin> medecins = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                UUID id = UUID.fromString(nextLine[0]);
                String nom = nextLine[1];
                String sexe = nextLine[2];
                LocalDate dateNaissance = LocalDate.parse(nextLine[3]);
                String specialite = nextLine[4];
                String email = nextLine[5];
                String cv = nextLine[6];
                Map<String, String> adresse = parseAdresse(nextLine[7]);
                Set<String> listTelephones = Set.of(nextLine[8].split(","));
                Set<String> listPrenoms = Set.of(nextLine[9].split(","));

                Medecin medecin = new Medecin(id, nom, sexe, dateNaissance, specialite, email, cv, adresse, listTelephones, listPrenoms);
                medecins.add(medecin);
            }

            insertMultipleMedecins(medecins);

        } catch (IOException | CsvException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    private Map<String, String> parseAdresse(String adresseStr) {
        return Map.of();
    }
}
