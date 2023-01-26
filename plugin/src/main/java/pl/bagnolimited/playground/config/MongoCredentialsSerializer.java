package pl.bagnolimited.playground.config;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import pl.bagnolimited.playground.feature.database.MongoCredentials;

final class MongoCredentialsSerializer implements ObjectSerializer<MongoCredentials> {

    @Override
    public boolean supports(@NonNull Class<? super MongoCredentials> type) {
        return MongoCredentials.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull MongoCredentials object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("hostname", object.getHostname());
        data.add("port", object.getPort());
        data.add("username", object.getUsername());
        data.add("password", object.getPassword());
        data.add("database", object.getDatabase());
    }

    @Override
    public MongoCredentials deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        final String hostname = data.get("hostname", String.class);
        final int port = data.get("port", Integer.class);
        final String username = data.get("username", String.class);
        final String password = data.get("password", String.class);
        final String database = data.get("database", String.class);
        return MongoCredentials.create(hostname, port, username, password, database);
    }

}