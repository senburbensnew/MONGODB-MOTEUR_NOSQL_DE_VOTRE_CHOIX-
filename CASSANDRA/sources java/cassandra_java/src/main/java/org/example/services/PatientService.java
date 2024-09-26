package org.example.services;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import org.example.entities.Patient;
import java.util.*;

public class PatientService {
    private CqlSession session;

    public PatientService(CqlSession session) {
        this.session = session;
        this.createIndexes();
    }

    public void insertOnePatient(Patient patient) {
        String query = "INSERT INTO Patient (id, numero_securite_sociale, nom, sexe, date_naissance, email, poids, hauteur, list_telephones, list_prenoms, adresse, allergies) " +
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
        String query = "INSERT INTO Patient (id, numero_securite_sociale, nom, sexe, date_naissance, email, poids, hauteur, list_telephones, list_prenoms, adresse, allergies) " +
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

    public void updatePatient(Patient patient) {
        String query = "UPDATE Patient SET email = ?, poids = ?, hauteur = ?, list_telephones = ?, list_prenoms = ?, adresse = ?, allergies = ? " +
                "WHERE id = ?";
        PreparedStatement preparedStatement = session.prepare(query);
        BoundStatement boundStatement = preparedStatement.bind(
                patient.getEmail(),
                patient.getPoids(),
                patient.getHauteur(),
                patient.getListTelephones(),
                patient.getListPrenoms(),
                patient.getAdresse(),
                patient.getAllergies(),
                patient.getId()
        );
        session.execute(boundStatement);
    }

    public void deletePatient(String date_naissance, UUID id) {
        String query = "DELETE FROM Patient WHERE date_naissance = ? AND id = ?";
        PreparedStatement preparedStatement = session.prepare(query);
        BoundStatement boundStatement = preparedStatement.bind(date_naissance, id);
        session.execute(boundStatement);
    }

    public List<Patient> getAllPatients() {
        String query = "SELECT * FROM Patient";
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

    public long countRecords() {
        String query = "SELECT COUNT(*) FROM Patient_By_BirthDay";
        ResultSet resultSet = session.execute(query);
        return resultSet.one().getLong(0);
    }

    public void createIndexes(){
        session.execute("CREATE INDEX IF NOT EXISTS ON Patient_By_BirthDay (nom)");
    }
}
