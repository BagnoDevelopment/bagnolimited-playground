package pl.bagnolimited.playground.feature.database;

import lombok.Data;

public final @Data(staticConstructor = "create") class MongoCredentials {

    private final String hostname;
    private final int port;
    private final String username;
    private final String password;
    private final String database;

}