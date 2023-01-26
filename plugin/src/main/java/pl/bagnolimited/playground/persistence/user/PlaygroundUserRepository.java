package pl.bagnolimited.playground.persistence.user;

import java.util.Optional;
import java.util.UUID;

public interface PlaygroundUserRepository {

    PlaygroundUser createUser(UUID uniqueId, String nickname);

    Optional<PlaygroundUser> findByUniqueId(UUID uniqueId);

    Optional<PlaygroundUser> findByNickname(String nickname);

}