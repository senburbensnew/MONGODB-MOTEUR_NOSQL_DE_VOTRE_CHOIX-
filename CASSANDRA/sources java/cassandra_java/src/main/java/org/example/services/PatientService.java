package org.example.services;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
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
}
