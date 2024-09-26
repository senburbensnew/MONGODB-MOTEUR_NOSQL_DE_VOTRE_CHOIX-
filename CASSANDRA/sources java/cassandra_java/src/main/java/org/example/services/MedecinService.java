package org.example.services;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.example.entities.Medecin;

public class MedecinService {
    private CqlSession session;

    public MedecinService(CqlSession session) {
        this.session = session;
    }

    public void insertOneMedecin(Medecin medecin) {
        String query = "INSERT INTO Medecin_By_Speciality (id, nom, sexe, date_naissance, specialite, email, cv, adresse, list_telephones, list_prenoms) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = session.prepare(query);

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

    // Insérer plusieurs enregistrements
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

    public Medecin getOneMedecin(String specialite, LocalDate dateNaissance, String nom) {
        String query = "SELECT id, nom, sexe, date_naissance, specialite, email, cv, adresse, list_telephones, list_prenoms " +
                "FROM Medecin_By_Speciality WHERE specialite = ? AND date_naissance = ? AND nom = ?";

        PreparedStatement preparedStatement = session.prepare(query);

        BoundStatement boundStatement = preparedStatement.bind(specialite, dateNaissance, nom);

        ResultSet resultSet = session.execute(boundStatement);

        Row row = resultSet.one();

        if (row != null) {
            UUID id = row.getUuid("id");
            String sexe = row.getString("sexe");
            String email = row.getString("email");
            String cv = row.getString("cv");
            Map<String, String> adresse = row.getMap("adresse", String.class, String.class);
            Set<String> listTelephones = row.getSet("list_telephones", String.class);
            Set<String> listPrenoms = row.getSet("list_prenoms", String.class);

            return new Medecin(id, nom, sexe, dateNaissance, specialite, email, cv, adresse, listTelephones, listPrenoms);
        }
        return null;
    }

    public void updateOneMedecin(Medecin medecin) {
        String query = "UPDATE Medecin_By_Speciality " +
                "SET cv = ?, adresse = ?, list_telephones = ?, list_prenoms = ? " +
                "WHERE specialite = ? AND date_naissance = ? AND nom = ? AND sexe = ? AND email = ? AND id = ?";

        PreparedStatement preparedStatement = session.prepare(query);

        BoundStatement boundStatement = preparedStatement.bind(
                medecin.getCv(),
                medecin.getAdresse(),
                medecin.getListTelephones(),
                medecin.getListPrenoms(),
                medecin.getSpecialite(),
                medecin.getDateNaissance(),
                medecin.getNom(),
                medecin.getSexe(),
                medecin.getEmail(),
                medecin.getId()
        );

        session.execute(boundStatement);
    }

    public void deleteOneMedecin(String specialite, LocalDate dateNaissance, String nom, String sexe, String email, UUID id) {
        String query = "DELETE FROM Medecin_By_Speciality " +
                "WHERE specialite = ? AND date_naissance = ? AND nom = ? AND sexe = ? AND email = ? AND id = ?";

        PreparedStatement preparedStatement = session.prepare(query);

        BoundStatement boundStatement = preparedStatement.bind(
                specialite,
                dateNaissance,
                nom,
                sexe,
                email,
                id
        );
        session.execute(boundStatement);
    }
    
    public void createIndex(){
        session.execute("CREATE INDEX IF NOT EXISTS ON Medecin (nom)");
    }
    // Jointure et Groupement
    // Dans Cassandra, les jointures ne sont pas supportées directement, donc il est recommandé d'organiser les données pour éviter les jointures en dénormalisant les données. Vous pouvez utiliser la requête de sélection par clé de partition ou le groupement via des agrégats externes.
    // Ces spécifications montrent comment modéliser et interagir avec les bases de données MongoDB et Cassandra, en tenant compte des particularités de chaque moteur.
}
