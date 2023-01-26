package pl.bagnolimited.playground.feature.database;

import com.mongodb.client.MongoClient;
import dev.morphia.Datastore;

public interface MongoService {

    MongoClient getMongoClient();

    Datastore getDatastore();

    Datastore getDatastore(String datastore);

}