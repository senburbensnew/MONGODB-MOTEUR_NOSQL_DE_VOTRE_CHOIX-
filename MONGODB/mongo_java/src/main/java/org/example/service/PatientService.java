package org.example.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.entity.Medecin;
import org.example.entity.Patient;

public class PatientService {
    private MongoCollection<Document> patientCollection;

    public PatientService(MongoDatabase database) {
        this.patientCollection = database.getCollection("Patient");
    }

    public void insertPatient(Patient patient) {
        Document doc = new Document("nom", patient.getNom());
                // .append("sexe", medecin.getSexe());
        patientCollection.insertOne(doc);
    }
}
