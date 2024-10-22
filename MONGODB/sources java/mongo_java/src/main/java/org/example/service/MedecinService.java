package org.example.service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.entity.Medecin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MedecinService {
    private final MongoCollection<Document> medecinCollection;

    public MedecinService(MongoDatabase database) {
        this.medecinCollection = database.getCollection("Medecin");
    }

    public InsertOneResult insertOneMedecin(Medecin medecin) {
        Document doc = new Document("nom", medecin.getNom())
                .append("sexe", medecin.getSexe())
                .append("date_naissance", medecin.getDateNaissance())
                .append("specialite", medecin.getSpecialite())
                .append("email", medecin.getEmail());
        return medecinCollection.insertOne(doc);
    }

    public void insertMedecins(List<Medecin> medecins) {
        List<Document> documents = new ArrayList<>();
        for (Medecin medecin : medecins) {
            Document doc = new Document("nom", medecin.getNom())
                    .append("sexe", medecin.getSexe())
                    .append("date_naissance", medecin.getDateNaissance())
                    .append("specialite", medecin.getSpecialite())
                    .append("email", medecin.getEmail());
            documents.add(doc);
        }
        medecinCollection.insertMany(documents);
    }

    public Medecin getMedecinById(String id) {
        Document query = new Document("_id", new ObjectId(id));
        Document result = medecinCollection.find(query).first();
        if (result != null) {
            Medecin medecin = new Medecin();
            medecin.setId(result.getObjectId(id));
            medecin.setNom(result.getString("nom"));
            medecin.setSpecialite(result.getString("specialite"));
            medecin.setEmail(result.getString("email"));
            medecin.setSexe(result.getString("sexe"));
            return medecin;
        }
        return null;
    }

    public void updateOneMedecin(String id, String email) {
        medecinCollection.updateOne(Filters.eq("_id", new ObjectId(id)), Updates.set("email", email));
    }

    public void updateMedecins(String specialite, String newEmailDomain) {
        medecinCollection.updateMany(Filters.eq("specialite", specialite),
                Updates.set("email", newEmailDomain));
    }

    public void deleteOneMedecin(ObjectId id) {
        medecinCollection.deleteOne(Filters.eq("_id", id));
    }

    public void deleteMedecins(String specialite) {
        medecinCollection.deleteMany(Filters.eq("specialite", specialite));
    }

    public void createIndex(String champ){
        medecinCollection.createIndex (Indexes.ascending(champ)) ;
    }

    // Rechercher tous les médecins ayant une spécialité donnée
    public List<Document> findMedecinsBySpecialite(String specialite) {
        return medecinCollection.find(Filters.eq("specialite", specialite)).into(new ArrayList<>());
    }

    // Grouper les médecins par spécialité et compter le nombre de médecins par groupe
    public List<Document> groupMedecinsBySpecialite() {
        return medecinCollection.aggregate(
                Arrays.asList(
                        Aggregates.group("$specialite", Accumulators.sum("count", 1))
                )
        ).into(new ArrayList<>());
    }

    // Effectuer une jointure entre la collection "Medecin" et "Patient" pour afficher les rendez-vous
    public List<Document> joinMedecinWithPatients() {
        return medecinCollection.aggregate(
                Arrays.asList(
                        Aggregates.lookup("Patient", "_id", "medecin", "list_rendez_vous")
                )
        ).into(new ArrayList<>());
    }

    // Rechercher les médecins ayant plus de N consultations
    public List<Document> findMedecinsWithMoreThanNConsultations(int n) {
        return medecinCollection.find(Filters.size("list_consultations", n)).into(new ArrayList<>());
    }

    // Grouper par ville et calculer le nombre total de médecins dans chaque ville
    public List<Document> groupMedecinsByCity() {
        return medecinCollection.aggregate(
                Arrays.asList(
                        Aggregates.group("$adresse.ville", Accumulators.sum("count", 1))
                )
        ).into(new ArrayList<>());
    }

    // Filtrer les médecins par sexe et trier par date de naissance
    public List<Document> filterMedecinsBySexAndSortByDate(String sexe) {
        return medecinCollection.find(Filters.eq("sexe", sexe))
                .sort(new Document("date_naissance", 1))
                .into(new ArrayList<>());
    }
}