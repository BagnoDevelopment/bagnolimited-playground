package pl.bagnolimited.playground.feature.database;

import com.google.inject.Singleton;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import pl.bagnolimited.playground.persistence.user.PlaygroundUser;
import pl.bagnolimited.playground.persistence.user.data.PlaygroundUserProperties;

import java.util.Collections;

public final @Singleton class SimpleMongoService implements MongoService {

    private final MongoClient mongoClient;
    private final Datastore datastore;

    public SimpleMongoService(MongoCredentials mongoCredentials) {
        final MongoCredential mongoCredential = MongoCredential.createCredential(
                mongoCredentials.getUsername(),
                mongoCredentials.getDatabase(),
                mongoCredentials.getPassword().toCharArray()
        );

        final CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
                .automatic(true).build());
        final CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);


        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        builder.hosts(Collections.singletonList(new ServerAddress(mongoCredentials.getHostname(), mongoCredentials.getPort()))))
                .credential(mongoCredential)
                .codecRegistry(codecRegistry)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .build();

        this.mongoClient = MongoClients.create(mongoClientSettings);
        this.datastore = Morphia.createDatastore(this.mongoClient, mongoCredentials.getDatabase());

        this.datastore.getMapper()
                .map(PlaygroundUser.class, PlaygroundUserProperties.class);
        this.datastore.ensureIndexes();
    }

    @Override
    public MongoClient getMongoClient() {
        return this.mongoClient;
    }

    @Override
    public Datastore getDatastore() {
        return this.datastore;
    }

    @Override
    public Datastore getDatastore(String datastore) {
        return Morphia.createDatastore(this.mongoClient, datastore);
    }

}