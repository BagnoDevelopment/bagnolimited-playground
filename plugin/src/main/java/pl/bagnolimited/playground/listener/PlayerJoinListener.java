package pl.bagnolimited.playground.listener;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.bagnolimited.playground.feature.database.MongoService;
import pl.bagnolimited.playground.persistence.user.PlaygroundUser;
import pl.bagnolimited.playground.persistence.user.PlaygroundUserRepository;

import java.util.Optional;

public final class PlayerJoinListener implements Listener {

    private final MongoService mongoService;
    private final PlaygroundUserRepository playgroundUserRepository;

    @Inject
    public PlayerJoinListener(Provider<MongoService> mongoServiceProvider, PlaygroundUserRepository playgroundUserRepository) {
        this.mongoService = mongoServiceProvider.get();
        this.playgroundUserRepository = playgroundUserRepository;
    }

    @EventHandler
    private void handle(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final Optional<PlaygroundUser> optionalPlaygroundUser = this.playgroundUserRepository.findByUniqueId(player.getUniqueId());

        optionalPlaygroundUser.ifPresentOrElse(playgroundUser -> {
            player.sendMessage("Znaleziono dane gracza.");
        }, () -> {
            final PlaygroundUser playgroundUser = this.playgroundUserRepository.createUser(player.getUniqueId(), player.getName());
            player.sendMessage("Utworzono dane gracza.");
            this.mongoService.getDatastore()
                    .save(playgroundUser);
        });
    }

}