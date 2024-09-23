package org.example.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.entity.Patient;

public class PatientService {
    private MongoCollection<Document> patientCollection;

    public PatientService(MongoDatabase database) {
        this.patientCollection = database.getCollection("Patient");
    }

    public void insertPatient(Patient patient) {
        Document addressDoc = new Document("numero", patient.getAdresse().getNumero())
                .append("rue", patient.getAdresse().getRue())
                .append("code_postal", patient.getAdresse().getCodePostal())
                .append("ville", patient.getAdresse().getVille());

        Document doc = new Document("numero_securite_sociale", patient.getNumeroSecuriteSociale())
                .append("nom", patient.getNom())
                .append("sexe", patient.getSexe())
                .append("date_naissance", patient.getDateNaissance())
                .append("email", patient.getEmail())
                .append("poids", patient.getPoids())
                .append("hauteur", patient.getHauteur())
                .append("list_telephones", patient.getListTelephones())
                .append("list_prenoms", patient.getListPrenoms())
                .append("adresse", addressDoc)
                .append("list_rendez_vous", patient.getListRendezVous())
                .append("list_consultations", patient.getListConsultations())
                .append("antecedents_medicaux", patient.getAntecedentsMedicaux())
                .append("allergies", patient.getAllergies());

        patientCollection.insertOne(doc);
    }

    public Patient getPatientById(String id) {
        Document query = new Document("_id", new ObjectId(id));
        Document result = patientCollection.find(query).first();
        if (result != null) {
            Patient patient = new Patient();
            patient.setId(result.getObjectId(id));
            patient.setNom(result.getString("nom"));
            patient.setEmail(result.getString("email"));
            patient.setSexe(result.getString("sexe"));
            return patient;
        }
        return null;
    }

    public void updatePatient(String id, String email) {
        patientCollection.updateOne(Filters.eq("_id", new ObjectId(id)), Updates.set("email", email));
    }

    public void deletePatient(String id) {
        patientCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    public void createIndex(String champ){
        patientCollection.createIndex (Indexes.ascending(champ)) ;
    }
}
