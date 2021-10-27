package cc.minetale.mlib.event;

import cc.minetale.commonlib.profile.Profile;
import lombok.Getter;
import net.minestom.server.event.Event;
import org.jetbrains.annotations.NotNull;

@Getter
public class AsyncProfileLoadEvent implements Event {

    private final Profile profile;

    public AsyncProfileLoadEvent(@NotNull Profile profile) {
        this.profile = profile;
    }

}
