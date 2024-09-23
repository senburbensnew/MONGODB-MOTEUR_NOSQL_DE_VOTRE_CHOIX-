package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.entity.Medecin;
import org.example.entity.Patient;
import org.example.service.MedecinService;
import org.example.service.PatientService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            medecinService.insertOneMedecin(medecin1);

            // Insere plusieurs medecins
            List<Medecin> medecins = new ArrayList<>();
            Medecin medecin2 = new Medecin();
            medecin2.setNom("Dupont");
            medecin2.setDateNaissance(new Date());
            medecin2.setSexe("F");
            medecin2.setSpecialite("Cardiologue");
            medecin2.setEmail("dupont@example.com");
            medecins.add(medecin2);

            Medecin medecin3 = new Medecin();
            medecin3.setNom("Martin");
            medecin3.setDateNaissance(new Date());
            medecin3.setSexe("M");
            medecin3.setSpecialite("Neurologue");
            medecin3.setEmail("martin@example.com");
            medecins.add(medecin3);

            medecinService.insertMedecins(medecins);

            // Recupere un medecin selon son id
            Medecin retrievedMedecin = medecinService.getMedecinById("66ef5ac8c3ca4e1820017abd");
            if (retrievedMedecin != null) {
                System.out.println(retrievedMedecin.toString());
            } else {
                System.out.println("Medecin not found");
            }

            // Modifie un medecin
            medecinService.updateOneMedecin("66ef5ac8c3ca4e1820017abd", "new_email@example.com");

            // Modifie plusieurs medecins
            medecinService.updateMedecins("Dentiste", "newdomain.com");

            // Supprime un medecin
            medecinService.deleteOneMedecin("66ef5ac8c3ca4e1820017abd");

            // Supprime plusieurs medecins
            medecinService.deleteMedecins("Chirurgien");

            // Insere un patient
            Patient patient1 = new Patient();
            patient1.setNom("Doe");
            patient1.setDateNaissance(new Date());
            patient1.setSexe("F");
            patient1.setEmail("doe@example.com");
            patientService.insertOnePatient(patient1);

            // insere plusieurs patients
            Patient patient2 = new Patient();
            patient2.setNom("Jane");
            patient2.setSexe("F");
            patient2.setEmail("jane@example.com");
            patient2.setDateNaissance(new Date());
            List<Patient> patients = List.of(patient1, patient2);
            patientService.insertPatients(patients);

            // Recupere un patient insere
            System.out.println(patientService.getPatientById("66ef5ac8c3ca4e1820017abd"));

            // Modifie un patient
            patientService.updateOnePatient("66ef5ac8c3ca4e1820017abd", "new_email@example.com");

            // modifie plusieurs patients
            patientService.updatePatients(70.0, 65.0);

            // Supprimer un patient
            patientService.deleteOnePatient("66ef5ac8c3ca4e1820017abd");

            // supprimer plusieurs patients

            patientService.deletePatients("2023-01-01");

            // 1. Rechercher tous les médecins ayant la spécialité "Cardiologue"
            List<Document> cardiologues = medecinService.findMedecinsBySpecialite("Cardiologue");
            cardiologues.forEach(System.out::println);

            // 2. Grouper les médecins par spécialité
            List<Document> groupedBySpecialite = medecinService.groupMedecinsBySpecialite();
            groupedBySpecialite.forEach(System.out::println);

            // 3. Jointure avec la collection "Patient" pour afficher les rendez-vous
            List<Document> medecinPatients = medecinService.joinMedecinWithPatients();
            medecinPatients.forEach(System.out::println);

            // 4. Rechercher les médecins ayant plus de 10 consultations
            List<Document> busyMedecins = medecinService.findMedecinsWithMoreThanNConsultations(10);
            busyMedecins.forEach(System.out::println);

            // 5. Grouper les médecins par ville
            List<Document> medecinsGroupedByCity = medecinService.groupMedecinsByCity();
            medecinsGroupedByCity.forEach(System.out::println);

            // 6. Filtrer les médecins de sexe "M" et trier par date de naissance
            List<Document> maleMedecins = medecinService.filterMedecinsBySexAndSortByDate("M");
            maleMedecins.forEach(System.out::println);

            // Test 1: Rechercher tous les patients ayant une allergie donnée
            String allergyToTest = "pollen";
            List<Document> patientsWithAllergy = patientService.findPatientsByAllergy(allergyToTest);
            System.out.println(patientsWithAllergy);

            // Test 2: Grouper les patients par sexe et compter le nombre de patients par groupe
            List<Document> groupedBySex = patientService.groupPatientsBySex();
            System.out.println(groupedBySex);

            // Test 3: Effectuer une jointure entre la collection "Patient" et "RendezVous"
            List<Document> patientsWithRendezVous = patientService.joinPatientsWithRendezVous();
            System.out.println(patientsWithRendezVous);

            // Test 4: Rechercher les patients ayant plus de N consultations
            int nConsultations = 5;
            List<Document> patientsWithMoreThanNConsultations = patientService.findPatientsWithMoreThanNConsultations(nConsultations);
            System.out.println(patientsWithMoreThanNConsultations);

            // Test 5: Grouper par ville et calculer le nombre total de patients dans chaque ville
            List<Document> patientsGroupedByCity = patientService.groupPatientsByCity();
            System.out.println(patientsGroupedByCity);

            // Test 6: Filtrer les patients par sexe et trier par date de naissance
            String sexeToTest = "F"; // For example, female
            List<Document> filteredBySex = patientService.filterPatientsBySexAndSortByDate(sexeToTest);
            System.out.println(filteredBySex);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}