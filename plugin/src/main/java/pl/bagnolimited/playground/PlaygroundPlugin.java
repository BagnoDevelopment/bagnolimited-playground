package pl.bagnolimited.playground;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import pl.bagnolimited.playground.listener.PlayerJoinListener;
import pl.bagnolimited.playground.listener.PlayerQuitListener;
import pl.bagnolimited.playground.module.MongoDatabaseModule;
import pl.bagnolimited.playground.module.OkaeriConfigModule;
import pl.bagnolimited.playground.persistence.user.PlaygroundUserRepository;
import pl.bagnolimited.playground.persistence.user.SimplePlaygroundUserRepository;

import java.io.File;
import java.util.Arrays;

@Plugin(name = "BagnoPlayground", version = "1.0.0")
@Description("Playground plugin for testing various things, may contain some hacky or just bad code.")
@ApiVersion(ApiVersion.Target.DEFAULT)
@Author("nwse")
public final class PlaygroundPlugin extends JavaPlugin implements Module {

    private Injector injector;

    @Override
    public void onEnable() {
        this.injector = Guice.createInjector(this);

        Arrays.asList(
                this.injector.getInstance(PlayerJoinListener.class),
                this.injector.getInstance(PlayerQuitListener.class)
        ).forEach(listener -> this.getServer().getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        /* Plugin disable logic. */
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(Server.class).toInstance(this.getServer());

        binder.install(new OkaeriConfigModule<>(
                PlaygroundConfig.class,
                new File(this.getDataFolder(), "config.yml")
        ));
        binder.install(new MongoDatabaseModule());

        binder.bind(PlaygroundUserRepository.class).toInstance(new SimplePlaygroundUserRepository());
    }

}