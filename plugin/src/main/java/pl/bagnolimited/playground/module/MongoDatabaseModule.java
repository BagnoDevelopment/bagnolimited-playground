package pl.bagnolimited.playground.module;

import com.google.inject.AbstractModule;
import pl.bagnolimited.playground.feature.database.MongoService;
import pl.bagnolimited.playground.module.mongo.MongoProvider;

public final class MongoDatabaseModule extends AbstractModule {

    @Override
    protected void configure() {
        this.bind(MongoService.class).toProvider(MongoProvider.class);
    }

}