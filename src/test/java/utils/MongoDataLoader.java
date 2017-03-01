package utils;

import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.Closeable;
import java.io.IOException;

public class MongoDataLoader implements Closeable {
    private final String mongoHost;

    private final String mongoPort;

    private MongoClient client;

    public MongoDataLoader(String mongoHost, String mongoPort) {
        this.mongoHost = mongoHost;
        this.mongoPort = mongoPort;
    }

    public  MongoCollection<Document> getCollection(String databaseName,String collectionName){
        MongoDatabase mongoDatabase = client.getDatabase(databaseName);
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
        return mongoCollection;
    }

    public void addRecord(String databaseName, String collectionName, String jsonData) {
        initialiseClient();
        MongoCollection<Document> mongoCollection = getCollection(databaseName,collectionName);
        Document document = Document.parse(jsonData);
        mongoCollection.insertOne(document);
    }

    public void emptyCollection(String databaseName, String collectionName) {
        initialiseClient();
        MongoCollection<Document> mongoCollection = getCollection(databaseName,collectionName);
        mongoCollection.deleteMany(new BasicDBObject());
    }

    public void deleteAllRecordFrom(String databaseName, String collectionName, String key, String value){
        initialiseClient();
        MongoCollection<Document> mongoCollection = getCollection(databaseName,collectionName);
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put(key,value);
        FindIterable<Document> field = mongoCollection.find(whereQuery);
        MongoCursor<Document> cursor = field.iterator();
        while(cursor.hasNext()) {
            mongoCollection.deleteMany(cursor.next());
        }
    }


    @Override
    public void close() throws IOException {
        client.close();
    }

    private synchronized void initialiseClient() {
        if (client == null) {
            client = new MongoClient(mongoHost, Integer.parseInt(mongoPort));
        }
    }
}
