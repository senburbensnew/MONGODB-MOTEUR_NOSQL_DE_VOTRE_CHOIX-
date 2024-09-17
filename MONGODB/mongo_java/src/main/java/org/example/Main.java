package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String connectionString = "mongodb://localhost:27017";

        // Create a MongoClient instance
        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            // Access a specific database
            MongoDatabase database = mongoClient.getDatabase("sample_mflix");
            // Check if connection is successful
            System.out.println("Connected to MongoDB database: " + database.getName());

            database.createCollection("movies");

            MongoCollection<Document> collection = database.getCollection("movies");

            collection.insertOne(new Document()
                    .append("_id", new ObjectId())
                    .append("title", "Silly Video")
                    .append("genres", Arrays.asList("Action", "Adventure")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}