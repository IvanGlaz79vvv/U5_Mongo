package org.example;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.BSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static com.mongodb.client.model.Indexes.ascending;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (var mongoClient = MongoClients.create()) {
            mongoClient.listDatabases();
            mongoClient.listDatabaseNames()
                    .forEach((Consumer<String>) System.out::println);

            var database = mongoClient.getDatabase("syn");

            MongoCollection<Document> todoCollection = database.getCollection("todo");

            /**>>>>>>>>> вставка <<<<<<<<<<<<*/
//            todoCollection = database.getCollection("todo");
//
//            var todoDocment = new Document(Map.of(
//                    "_id", new ObjectId(),
//                    "task", "Drynk some coffee",
//                    "dataCreated", LocalDateTime.now(),
//                    "done", false));
//
//            todoCollection.insertOne(todoDocment);
//            database.listCollectionNames()
//                    .forEach((Consumer<String>) System.out::println);
//            database.listCollections()
//                    .forEach((Consumer<Document>) System.out::println);

            /**>>>>>>>>>>>> поиск <<<<<<<<<<*/
//            todoCollection.find(new Document("task", new Document("$regex", "coffee")))
//                    .forEach((Consumer<Document>) System.out::println);
//            todoCollection.find()
//                    .forEach((Consumer<Document>) System.out::println);

            /**>>>>>>>>>>> замена <<<<<<<<<<*/
//            todoCollection.updateOne(new Document("_id", new ObjectId("658217f3a9766c15e029be63")),
//                    new Document(Map.of("$set", new Document("done", true),
//                            "$currentDate", new Document("dateDone", true),
//                            "$unset", new Document("dateCreated", true)
//                    ))
//            );

            /**>>>>>>>>>>>> удаление <<<<<<<<<<*/
//            todoCollection.deleteOne(new Document("_id", new ObjectId("658217f3a9766c15e029be63")));


            /**>>>>>>>>>>>> ЗАДАНИЯ <<<<<<<<<<<<<<*/
            System.out.println("\n>>>>>> запрос MongoDB для отображения всех данных из представленной таблицы <<<<<<");
            todoCollection.find()
                    .forEach((Consumer<Document>) System.out::println);


            System.out.println("\n>>>>> запрос MongoDB для отображения ФИО и даты рождения всех лиц из представленной таблицы <<<<");
            todoCollection.find().projection(Projections.include("FIRST_NAME", "LAST_NAME", "HIRE_DATE"))
                    .forEach((Consumer<Document>) System.out::println);


            System.out.println("\n>>>> запрос MongoDB для отображения всех работников сортируя их в порядке уменьшения заработной платы <<<<<");
            FindIterable<Document> cursor = todoCollection.find().sort(new BasicDBObject("SALARY", -1)).projection(Projections.include("FIRST_NAME", "LAST_NAME", "SALARY"));
            MongoCursor<Document> iterator = cursor.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

            System.out.println("\n>>>> запрос MongoDB для отображения средней зарплаты всех работников <<<<<");
            FindIterable<Document> cursor2 = todoCollection.find().sort(new BasicDBObject("SALARY", -1)).projection(Projections.include("FIRST_NAME", "LAST_NAME", "SALARY"));
            MongoCursor<Document> iterator2 = cursor2.iterator();
            int resAVG = 0;
            int count = 0;
            while (iterator2.hasNext()) {
                count++;
                resAVG += (int) iterator2.next().get("SALARY");
            }
            System.out.println("resAVG = " + resAVG/count);

            System.out.println("\n>>>>> запрос MongoDB для отображения только имени и номера телефонасотрудников из представленной таблицы <<<<");
            todoCollection.find().projection(Projections.include("FIRST_NAME", "PHONE_NUMBER"))
                    .forEach((Consumer<Document>) System.out::println);


            //получение индексов
//            todoCollection.listIndexes()
//                    .forEach((Consumer<Document>) System.out::println);

        }
    }
}