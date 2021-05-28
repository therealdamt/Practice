package xyz.damt.handler;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import xyz.damt.Practice;

import javax.print.Doc;

@Getter @Setter
public class MongoHandler {

    private final Practice practice;
    
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;

    private MongoCollection<Document> profiles;
    private MongoCollection<Document> arena;
    private MongoCollection<Document> kits;

    public MongoHandler(Practice practice) {
        this.practice = practice;
        this.loadDatabase();
    }

    private void loadDatabase() {
        if (practice.getConfigHandler().getDatabaseHandler().MONGO_HAS_AUTH) {
            mongoClient = new MongoClient(
                    new ServerAddress(practice.getConfigHandler().getDatabaseHandler().MONGO_HOST, practice.getConfigHandler().getDatabaseHandler().MONGO_PORT),
                    MongoCredential.createCredential(
                            practice.getConfigHandler().getDatabaseHandler().MONGO_USERNAME,
                            practice.getConfigHandler().getDatabaseHandler().MONGO_AUTH_DATABASE,
                            practice.getConfigHandler().getDatabaseHandler().MONGO_PASSWORD.toCharArray()
                    ),
                    MongoClientOptions.builder().build()
            );
            mongoClient = new MongoClient(practice.getConfigHandler().getDatabaseHandler().MONGO_HOST,
                    practice.getConfigHandler().getDatabaseHandler().MONGO_PORT);
        } else {
            mongoClient = new MongoClient(practice.getConfigHandler().getDatabaseHandler().MONGO_HOST,
                    practice.getConfigHandler().getDatabaseHandler().MONGO_PORT);
        }

        mongoDatabase = mongoClient.getDatabase("practice");

        profiles = mongoDatabase.getCollection("profiles");
        arena = mongoDatabase.getCollection("arena");
        kits = mongoDatabase.getCollection("kits");
    }

}
