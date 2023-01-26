package pl.bagnolimited.playground.persistence.user;

import dev.morphia.annotations.*;
import lombok.Data;
import org.bson.types.ObjectId;
import pl.bagnolimited.playground.persistence.user.data.PlaygroundUserProperties;

import java.util.UUID;

@Entity("users")
@Indexes({
        @Index(fields = @Field("uniqueId")),
        @Index(fields = @Field("nickname"))
})
public final @Data class PlaygroundUser {

    @Id
    private final ObjectId objectId;

    private final UUID uniqueId;
    /* Test */
    private final PlaygroundUserProperties properties;
    private String nickname;

}