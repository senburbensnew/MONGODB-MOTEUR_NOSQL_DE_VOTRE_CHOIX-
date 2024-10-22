package org.example.services;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.example.entities.Patient;

import java.time.LocalDate;
import java.util.*;

public class PatientService {
    private CqlSession session;

    public PatientService(CqlSession session) {
        this.session = session;
    }

    public void insertOnePatient(Patient patient) {
        String query = "INSERT INTO patient_by_birthday (id, numero_securite_sociale, nom, sexe, date_naissance, email, poids, hauteur, list_telephones, list_prenoms, adresse, allergies) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = session.prepare(query);

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

    public void insertMultiplePatients(List<Patient> patients) {
        String query = "INSERT INTO patient_by_birthday (id, numero_securite_sociale, nom, sexe, date_naissance, email, poids, hauteur, list_telephones, list_prenoms, adresse, allergies) " +
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

    public Patient getOnePatient(LocalDate dateNaissance, String nom, String sexe, String email) {
        String query = "SELECT * FROM patient_by_birthday WHERE date_naissance = ? AND nom = ? AND sexe = ? AND email = ?";
        PreparedStatement preparedStatement = session.prepare(query);
        BoundStatement boundStatement = preparedStatement.bind(
                dateNaissance,
                nom,
                sexe,
                email
        );
        ResultSet resultSet = session.execute(boundStatement);
        Row row = resultSet.one();

        if (row != null) {
            Patient patient = new Patient();
            patient.setId(row.getUuid("id"));
            patient.setNom(row.getString("nom"));
            patient.setSexe(row.getString("sexe"));
            patient.setDateNaissance(row.getLocalDate("date_naissance"));
            patient.setEmail(row.getString("email"));
            patient.setPoids(row.getDouble("poids"));
            patient.setHauteur(row.getDouble("hauteur"));

            return patient;
        }
        return null;
    }

    public List<Patient> getPatientsByBirthday(LocalDate dateNaissance){
        String query = "SELECT * FROM patient_by_birthday WHERE date_naissance = ?";
        PreparedStatement preparedStatement = session.prepare(query);
        BoundStatement boundStatement = preparedStatement.bind(dateNaissance);
        ResultSet resultSet = session.execute(boundStatement);
        List<Patient> patients = new ArrayList<>();

        for (Row row : resultSet) {
            Patient patient = new Patient(
                    row.getUuid("id"),
                    row.getString("numero_securite_sociale"),
                    row.getString("nom"),
                    row.getString("sexe"),
                    row.getLocalDate("date_naissance"),
                    row.getString("email"),
                    row.getDouble("poids"),
                    row.getDouble("hauteur"),
                    row.getSet("list_telephones", String.class),
                    row.getSet("list_prenoms", String.class),
                    row.getMap("adresse", String.class, String.class),
                    row.getList("allergies", String.class)
            );
            patients.add(patient);
        }

        return patients;
    }

    public List<Patient> getAllPatients() {
        String query = "SELECT * FROM patient_by_birthday";
        ResultSet resultSet = session.execute(query);
        List<Patient> patients = new ArrayList<>();

        for (Row row : resultSet) {
            Patient patient = new Patient(
                    row.getUuid("id"),
                    row.getString("numero_securite_sociale"),
                    row.getString("nom"),
                    row.getString("sexe"),
                    row.getLocalDate("date_naissance"),
                    row.getString("email"),
                    row.getDouble("poids"),
                    row.getDouble("hauteur"),
                    row.getSet("list_telephones", String.class),
                    row.getSet("list_prenoms", String.class),
                    row.getMap("adresse", String.class, String.class),
                    row.getList("allergies", String.class)
            );
            patients.add(patient);
        }
        return patients;
    }

    public void updateOnePatient(Patient patient) {
        String query = "UPDATE patient_by_birthday SET poids = ?, hauteur = ? " +
                "WHERE date_naissance = ? AND nom = ? AND sexe = ? AND email = ? AND id = ?";
        PreparedStatement preparedStatement = session.prepare(query);
        BoundStatement boundStatement = preparedStatement.bind(
                patient.getPoids(),
                patient.getHauteur(),
                patient.getDateNaissance(),
                patient.getNom() + " test",
                patient.getSexe(),
                patient.getEmail(),
                patient.getId()
        );
        session.execute(boundStatement);
    }

    public void deleteOnePatient(Patient patient) {
        String query = "DELETE FROM patient_by_birthday WHERE date_naissance = ? AND nom = ? AND sexe = ? AND email = ? AND id = ?";
        PreparedStatement preparedStatement = session.prepare(query);
        BoundStatement boundStatement = preparedStatement.bind(
                patient.getDateNaissance(),
                patient.getNom(),
                patient.getSexe(),
                patient.getEmail(),
                patient.getId());
        session.execute(boundStatement);
    }

    public void deletePatients(LocalDate dateNaissance){
        String query = "DELETE FROM patient_by_birthday WHERE date_naissance = ?";
        PreparedStatement preparedStatement = session.prepare(query);
        BoundStatement boundStatement = preparedStatement.bind(dateNaissance);
        session.execute(boundStatement);
    }

    public long countRecords() {
        String query = "SELECT COUNT(*) FROM Patient_By_BirthDay";
        ResultSet resultSet = session.execute(query);
        return resultSet.one().getLong(0);
    }

    public void createIndexes(){
        session.execute("CREATE INDEX IF NOT EXISTS ON Patient_By_BirthDay (nom)");
    }

    // Dans Cassandra, les jointures ne sont pas supportées directement, donc il est recommandé d'organiser les données pour éviter les jointures en dénormalisant les données.
    // La requête de sélection par clé de partition ou le groupement via des agrégats externes.
}
