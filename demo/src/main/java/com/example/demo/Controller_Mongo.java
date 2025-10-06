package com.example.demo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Controller_Mongo {
    private static MongoDatabase database;


    public static MongoDatabase getDatabase() {
        if (database == null) {
            try {
                String connectionString = "mongodb+srv://bferfer:QZ1zBVzYrZilu6d4@cluster0.tgbjhs7.mongodb.net/Ficha_RH?retryWrites=true&w=majority";

                ServerApi serverApi = ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build();

                MongoClientSettings settings = MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(connectionString))
                        .serverApi(serverApi)
                        .build();

                MongoClient mongoClient = MongoClients.create(settings);
                database = mongoClient.getDatabase("Ficha_RH");
                System.out.println("Conectado a la base de datos Ficha_RH");
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
        return database;
    }
}
