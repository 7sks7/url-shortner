package com.example.url_shortner.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MongoTest {

    private static MongoDatabase database;

    @BeforeAll
    public static void setup() {
        // Use environment variables for credentials (optional, safer)
        String user = System.getenv().getOrDefault("MONGO_USER", "johnad3687_db_user");
        String pass = System.getenv().getOrDefault("MONGO_PASS", "Vickypubg@41").replace("@", "%40");
        String dbName = System.getenv().getOrDefault("MONGO_DB", "myDatabase");

        String uri = "mongodb+srv://" + user + ":" + pass + "@url-cluster.n3z2nfj.mongodb.net/" + dbName + "?retryWrites=true&w=majority";

        MongoClient mongoClient = MongoClients.create(uri);
        database = mongoClient.getDatabase(dbName);
    }

    @Test
    public void testConnection() {
        System.out.println("Connected to MongoDB Atlas: " + database.getName());
    }
}