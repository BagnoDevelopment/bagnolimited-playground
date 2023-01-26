package pl.bagnolimited.playground.persistence.user;

import com.google.inject.Singleton;
import org.bson.types.ObjectId;
import pl.bagnolimited.playground.persistence.user.data.PlaygroundUserProperties;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final @Singleton class SimplePlaygroundUserRepository implements PlaygroundUserRepository {

    private final Map<UUID, PlaygroundUser> uuidPlaygroundUserMap = new ConcurrentHashMap<>();
    private final Map<String, PlaygroundUser> nicknamePlaygroundUserMap = new ConcurrentHashMap<>();

    @Override
    public PlaygroundUser createUser(UUID uniqueId, String nickname) {
        final PlaygroundUser playgroundUser = new PlaygroundUser(
                new ObjectId(),
                uniqueId,
                new PlaygroundUserProperties()
        );
        playgroundUser.setNickname(nickname);

        this.uuidPlaygroundUserMap.put(uniqueId, playgroundUser);
        this.nicknamePlaygroundUserMap.put(nickname, playgroundUser);

        return playgroundUser;
    }

    @Override
    public Optional<PlaygroundUser> findByUniqueId(UUID uniqueId) {
        return Optional.ofNullable(this.uuidPlaygroundUserMap.get(uniqueId));
    }

    @Override
    public Optional<PlaygroundUser> findByNickname(String nickname) {
        return Optional.ofNullable(this.nicknamePlaygroundUserMap.get(nickname));
    }

}