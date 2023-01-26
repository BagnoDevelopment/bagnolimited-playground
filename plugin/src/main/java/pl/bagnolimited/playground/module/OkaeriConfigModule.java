package pl.bagnolimited.playground.module;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import pl.bagnolimited.playground.config.SerdesPlayground;

import java.io.File;

public final class OkaeriConfigModule<T extends OkaeriConfig> extends AbstractModule {

    private final T config;
    private final Class<T> type;

    public OkaeriConfigModule(Class<T> type, File file) {
        this.type = type;

        this.config = ConfigManager.create(type, (it) -> {
            it.withConfigurer(new YamlBukkitConfigurer(), new SerdesPlayground());
            it.withBindFile(file);
            it.saveDefaults();
            it.load();
        });
    }

    @Override
    protected void configure() {
        this.bind(TypeLiteral.get(this.type)).toInstance(this.config);
    }

}