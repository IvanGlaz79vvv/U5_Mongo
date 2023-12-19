package org.example;

import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) throws SQLException{
        try (var mongoClient = MongoClients.create()) {
            mongoClient.listDatabases();
            mongoClient.listDatabaseNames()
                    .forEach((Consumer<String>) System.out::println);

            var database = mongoClient.getDatabase("syn");

            database.listCollectionNames()
                    .forEach((Consumer<String>) System.out::println);
            database.listCollections()
                    .forEach((Consumer<Document>) System.out::println);

            var todoCollection = database.getCollection("todo");

//            todoCollection.find(new Document("task", new Document("$regex", "coffee")))
//                    .forEach((Consumer<Document>) System.out::println);
            todoCollection.find()
                    .forEach((Consumer<Document>) System.out::println);;

            todoCollection = database.getCollection("todo");

//            var todoDocment = new Document(Map.of(
//                    "_id", new ObjectId(),
//                    "task", "Drynk some coffee",
//                    "dataCreated", LocalDateTime.now(),
//                    "done", false));
//
//            todoCollection.insertOne(todoDocment);
        }
    }
}