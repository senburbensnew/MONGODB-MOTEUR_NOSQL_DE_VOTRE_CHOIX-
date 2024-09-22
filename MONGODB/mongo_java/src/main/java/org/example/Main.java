package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.example.entity.Medecin;
import org.example.service.MedecinService;
import org.example.service.PatientService;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        String connectionString = "mongodb://localhost:27017";

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase database = mongoClient.getDatabase("cabinet_medical");
            System.out.println("Connected to MongoDB database: " + database.getName());

            MedecinService medecinService = new MedecinService(database);
            PatientService patientService = new PatientService(database);

            // Insere un medecin
            Medecin medecin1 = new Medecin();
            medecin1.setNom("Milorme");
            medecin1.setDateNaissance(new Date());
            medecin1.setSexe("M");
            medecin1.setSpecialite("Dentiste");
            medecin1.setEmail("email@example.com");
            medecinService.insertMedecin(medecin1);

            // Insere un patient

            // Recupere un medecin selon son id
            Medecin medecin1AfterInsert = medecinService.getMedecinById("66ef5ac8c3ca4e1820017abd");
            System.out.println(medecin1AfterInsert.toString());

            // Recupere le patient insere

            // Modifie un medecin
            medecinService.updateMedecin("66ef5ac8c3ca4e1820017abd", "new_email@example.com");

            // Modifie le patient

            // Supprime un medecin
            medecinService.deleteMedecin("66ef5ac8c3ca4e1820017abd");

            // Supprimer un patient

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}