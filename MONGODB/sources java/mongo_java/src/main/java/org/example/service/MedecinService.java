package org.example.service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.example.entity.Medecin;

import java.util.Arrays;

public class MedecinService {
    private final MongoCollection<Document> medecinCollection;

    public MedecinService(MongoDatabase database) {
        this.medecinCollection = database.getCollection("Medecin");
    }

    public void insertOneMedecin(Medecin medecin) {
        Document doc = new Document("nom", medecin.getNom())
                .append("sexe", medecin.getSexe())
                .append("date_naissance", medecin.getDateNaissance())
                .append("specialite", medecin.getSpecialite())
                .append("email", medecin.getEmail());
        medecinCollection.insertOne(doc);
    }

    public void insertMedecins(){

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

    public void updateMedecins(){

    }

    public void deleteOneMedecin(String id) {
        medecinCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    public void deleteMedecins(){

    }

    public void createIndex(String champ){
        medecinCollection.createIndex (Indexes.ascending(champ)) ;
    }

    public void groupMedecinsBySpecialite() {
        AggregateIterable<Document> groupResult = medecinCollection.aggregate(Arrays.asList(
                Aggregates.group("$specialite", Accumulators.sum("total", 1))
        ));

        for (Document doc : groupResult) {
            System.out.println(doc.toJson());
        }
    }
}
