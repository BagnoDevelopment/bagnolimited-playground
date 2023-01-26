package pl.bagnolimited.playground;

import eu.okaeri.configs.OkaeriConfig;
import pl.bagnolimited.playground.feature.database.MongoCredentials;

public final class PlaygroundConfig extends OkaeriConfig {

    public MongoCredentials mongoCredentials = MongoCredentials.create("mongodb://localhost", 27017, "admin", "SuperSecretPa$$word", "example");

}