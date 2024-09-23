package org.example.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.entity.Patient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PatientService {
    private final MongoCollection<Document> patientCollection;

    public PatientService(MongoDatabase database) {
        this.patientCollection = database.getCollection("Patient");
    }

    public void insertOnePatient(Patient patient) {
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

    public void insertPatients(List<Patient> patients) {
        List<Document> documents = new ArrayList<>();
        for (Patient patient : patients) {
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

            documents.add(doc);
        }
        patientCollection.insertMany(documents);
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

    public void updateOnePatient(String id, String email) {
        patientCollection.updateOne(Filters.eq("_id", new ObjectId(id)), Updates.set("email", email));
    }

    public void updatePatients(double weightThreshold, double newWeight) {
        patientCollection.updateMany(Filters.gt("poids", weightThreshold), Updates.set("poids", newWeight));
    }

    public void deleteOnePatient(String id) {
        patientCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    public void deletePatients(String dateThreshold) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateThreshold);

            patientCollection.deleteMany(Filters.lt("date_naissance", date));
            System.out.println("Patients with date_naissance earlier than " + dateThreshold + " have been deleted.");
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Invalid date format. Please use 'yyyy-MM-dd'.");
        }
    }

    public void createIndex(String champ){
        patientCollection.createIndex (Indexes.ascending(champ)) ;
    }

    // Rechercher tous les patients ayant une allergie donnée
    public List<Document> findPatientsByAllergy(String allergy) {
        return patientCollection.find(Filters.eq("allergies", allergy)).into(new ArrayList<>());
    }

    // Grouper les patients par sexe et compter le nombre de patients par groupe
    public List<Document> groupPatientsBySex() {
        return patientCollection.aggregate(
                Arrays.asList(
                        Aggregates.group("$sexe", Accumulators.sum("count", 1))
                )
        ).into(new ArrayList<>());
    }

    // Effectuer une jointure entre la collection "Patient" et "RendezVous" pour afficher les rendez-vous
    public List<Document> joinPatientsWithRendezVous() {
        return patientCollection.aggregate(
                Arrays.asList(
                        Aggregates.lookup("RendezVous", "_id", "patientId", "list_rendez_vous")
                )
        ).into(new ArrayList<>());
    }

    // Rechercher les patients ayant plus de N consultations
    public List<Document> findPatientsWithMoreThanNConsultations(int n) {
        return patientCollection.find(Filters.size("list_consultations", n)).into(new ArrayList<>());
    }

    // Grouper par ville et calculer le nombre total de patients dans chaque ville
    public List<Document> groupPatientsByCity() {
        return patientCollection.aggregate(
                Arrays.asList(
                        Aggregates.group("$adresse.ville", Accumulators.sum("count", 1))
                )
        ).into(new ArrayList<>());
    }

    // Filtrer les patients par sexe et trier par date de naissance
    public List<Document> filterPatientsBySexAndSortByDate(String sexe) {
        return patientCollection.find(Filters.eq("sexe", sexe))
                .sort(new Document("date_naissance", 1))
                .into(new ArrayList<>());
    }

}
