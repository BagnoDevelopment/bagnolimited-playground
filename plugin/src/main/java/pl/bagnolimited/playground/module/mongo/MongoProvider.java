package pl.bagnolimited.playground.module.mongo;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import pl.bagnolimited.playground.PlaygroundConfig;
import pl.bagnolimited.playground.feature.database.MongoService;
import pl.bagnolimited.playground.feature.database.SimpleMongoService;

public final @Singleton class MongoProvider implements Provider<MongoService> {

    private final PlaygroundConfig playgroundConfig;

    @Inject
    public MongoProvider(PlaygroundConfig playgroundConfig) {
        this.playgroundConfig = playgroundConfig;
    }

    @Override
    public MongoService get() {
        return new SimpleMongoService(this.playgroundConfig.mongoCredentials);
    }

}