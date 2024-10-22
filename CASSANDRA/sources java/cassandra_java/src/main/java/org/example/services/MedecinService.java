package org.example.services;

import java.time.LocalDate;
import java.util.*;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.example.entities.Medecin;

public class MedecinService {
    private CqlSession session;

    public MedecinService(CqlSession session) {
        this.session = session;
    }

    // Inserer un medecin
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

    // Recuperer un medecin
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

    // Recuperer plusieurs medecins
    public List<Medecin> getMedecins(String specialite) {
        List<Medecin> medecins = new ArrayList<>();

        String query = "SELECT id, nom, sexe, date_naissance, specialite, email, cv, adresse, list_telephones, list_prenoms " +
                "FROM Medecin_By_Speciality WHERE specialite = ?";

        PreparedStatement preparedStatement = session.prepare(query);
        BoundStatement boundStatement = preparedStatement.bind(specialite);
        ResultSet resultSet = session.execute(boundStatement);

        for (Row row : resultSet) {
            UUID id = row.getUuid("id");
            String nom = row.getString("nom");
            String sexe = row.getString("sexe");
            LocalDate dateNaissance = row.getLocalDate("date_naissance");
            String email = row.getString("email");
            String cv = row.getString("cv");
            Map<String, String> adresse = row.getMap("adresse", String.class, String.class);
            Set<String> listTelephones = row.getSet("list_telephones", String.class);
            Set<String> listPrenoms = row.getSet("list_prenoms", String.class);

            Medecin medecin = new Medecin(id, nom, sexe, dateNaissance, specialite, email, cv, adresse, listTelephones, listPrenoms);
            medecins.add(medecin);
        }

        return medecins;
    }

    // Modifier un medecin
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

    // Supprimer un medecin
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

    // Supprimer plusieurs medecins en fonction de leur specialite
    public void deleteMedecins(String specialite) {
        String query = "DELETE FROM Medecin_By_Speciality WHERE specialite = ?";
        PreparedStatement preparedStatement = session.prepare(query);
        BoundStatement boundStatement = preparedStatement.bind(specialite);
        session.execute(boundStatement);
    }

    // Creation d'index
    public void createIndexes(){
        session.execute("CREATE INDEX IF NOT EXISTS ON Medecin_By_Speciality (nom)");
    }

    // Groupement
    public long countRecords(String specialite) {
        String query = "SELECT COUNT(*) FROM Medecin_By_Speciality WHERE specialite = '" + specialite + "'";
        ResultSet resultSet = session.execute(query);
        return resultSet.one().getLong(0);
    }

    // Dans Cassandra, les jointures ne sont pas supportées directement, donc il est recommandé d'organiser les données pour éviter les jointures en dénormalisant les données.
    // La requête de sélection par clé de partition ou le groupement via des agrégats externes.
}
