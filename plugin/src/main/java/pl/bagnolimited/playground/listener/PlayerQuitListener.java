package pl.bagnolimited.playground.listener;

import com.google.inject.Inject;
import com.google.inject.Provider;
import dev.morphia.UpdateOptions;
import dev.morphia.query.experimental.filters.Filters;
import dev.morphia.query.experimental.updates.UpdateOperators;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.bagnolimited.playground.feature.database.MongoService;
import pl.bagnolimited.playground.persistence.user.PlaygroundUser;
import pl.bagnolimited.playground.persistence.user.PlaygroundUserRepository;

import java.util.Optional;

public final class PlayerQuitListener implements Listener {

    private final MongoService mongoService;
    private final PlaygroundUserRepository playgroundUserRepository;

    @Inject
    public PlayerQuitListener(Provider<MongoService> mongoServiceProvider, PlaygroundUserRepository playgroundUserRepository) {
        this.mongoService = mongoServiceProvider.get();
        this.playgroundUserRepository = playgroundUserRepository;
    }

    @EventHandler
    private void handle(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final Optional<PlaygroundUser> optionalPlaygroundUser = this.playgroundUserRepository.findByUniqueId(player.getUniqueId());

        optionalPlaygroundUser.ifPresent(playgroundUser -> {
            this.mongoService.getDatastore()
                    .find(PlaygroundUser.class)
                    .filter(Filters.eq("uniqueId", playgroundUser.getUniqueId()))
                    .update(UpdateOperators.set(playgroundUser))
                    .execute(new UpdateOptions().upsert(true));
        });
    }

}