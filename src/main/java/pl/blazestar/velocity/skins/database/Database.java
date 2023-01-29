package pl.blazestar.velocity.skins.database;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import org.slf4j.Logger;
import pl.blazestar.velocity.skins.database.annotation.Insertable;


import java.util.logging.LogManager;


public class Database {

    private final MongoClient client;
    private final MongoDatabase database;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public Database(String connectionString, String databaseName) {
        client = MongoClients.create(connectionString);
        database = client.getDatabase(databaseName).withCodecRegistry(
                CodecRegistries.fromRegistries(
                        CodecRegistries.fromProviders(new UuidCodecProvider(UuidRepresentation.STANDARD)),
                        MongoClientSettings.getDefaultCodecRegistry()
                ));
    }

    /**
     * Gets the mongodb collection from the database
     *
     * @param collectionName of the mongodb collection
     * @return the collection
     */
    public MongoCollection<Document> getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    /**
     * Inserts a single document into the mongodb collection
     *
     * @param collectionName of the mongodb collection
     * @param document       to be inserted into the collection
     */
    public void insertOne(String collectionName, Document document) {
        getCollection(collectionName).insertOne(document);
    }

    /**
     * Inserts the provided documents into the collection
     *
     * @param collectionName of the mongodb collection
     * @param documents      to be inserted into the collection
     */
    public void insertMany(String collectionName, Document... documents) {
        for (Document document : documents) {
            insertOne(collectionName, document);
        }
    }

    /**
     * Searches for a document matching the criteria and deletes it from the collection
     *
     * @param collectionName of the mongodb collection
     * @param key            of the search
     * @param value          of the search
     */
    public void deleteOne(String collectionName, String key, String value) {
        getCollection(collectionName).deleteOne(new Document(key, value));
    }

    /**
     * Searches for documents matching the key and the value and deletes them from the collection
     *
     * @param collectionName if the mongodb collection
     * @param key            of the search
     * @param value          of the search
     */
    public void deleteMany(String collectionName, String key, String value) {
        getCollection(collectionName).deleteMany(new Document(key, value));
    }

    /**
     * Searches for a document and replaces it with the provided Document
     *
     * @param collectionName of the mongodb collection
     * @param key            of the search
     * @param value          of the search
     * @param toReplace      document that is being replaced
     */
    public void replaceOne(String collectionName, String key, String value, Document toReplace) {
        getCollection(collectionName).findOneAndReplace(new Document(key, value), toReplace);
    }

    /**
     * Looks for a database entry with the given key and value
     *
     * @param collectionName of the mongodb collection
     * @param key            of the search
     * @param value          of the search
     * @return FindIterable with the found documents
     */
    public FindIterable<Document> find(String collectionName, String key, String value) {
        return getCollection(collectionName).find(new Document(key, value));
    }

    /**
     * Inserts the object into the database after converting it to json
     *
     * @param object to insert into the collection registered with the Insertable annotation
     * @throws RuntimeException if the class isn't annotated with Insertable
     */
    public void insert(Object object) {
        Class<?> clazz = object.getClass();
        if (!clazz.isAnnotationPresent(Insertable.class)) {
            throw new RuntimeException("Trying to insert a non insertable class " + clazz.getName());
        }

        Insertable insertable = clazz.getDeclaredAnnotation(Insertable.class);
        insertOne(insertable.collection(), Document.parse(gson.toJson(object)));
    }

    /**
     * Updates the object in the database after converting it to json
     *
     * @param entity to update in the collection registered with the Insertable annotation
     * @param key    of the mongodb document
     * @param value  of the mongodb document
     * @throws RuntimeException if the class isn't annotated with Insertable
     */
    public <T> void update(T entity, String key, String value) {
        Class<?> clazz = entity.getClass();
        if (!clazz.isAnnotationPresent(Insertable.class)) {
            throw new RuntimeException("Trying to insert a non insertable class " + clazz.getName());
        }

        Insertable insertable = clazz.getDeclaredAnnotation(Insertable.class);

        replaceOne(insertable.collection(), key, value, Document.parse(gson.toJson(entity)));
    }

    /**
     * Deletes the provided object from the mongodb collection
     *
     * @param entity to be deleted
     * @param key    of the mongodb document
     * @param value  of the mongodb document
     * @throws RuntimeException if the object isn't registered with the Insertable annotation
     */
    public <T> void delete(T entity, String key, String value) {
        Class<?> clazz = entity.getClass();
        if (!clazz.isAnnotationPresent(Insertable.class)) {
            throw new RuntimeException("Trying to insert a non insertable class " + clazz.getName());
        }

        Insertable insertable = clazz.getDeclaredAnnotation(Insertable.class);
        deleteOne(insertable.collection(), key, value);
    }

    public MongoClient getClient() {
        return client;
    }

    public MongoDatabase getDatabase() {
        return database;
    }


    public Gson getGson() {
        return gson;
    }
}