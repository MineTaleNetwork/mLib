package cc.minetale.mlib.npc;

import lombok.Getter;
import lombok.Setter;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.PlayerSkin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class NPCEntity extends LivingEntity {

    @Setter private String username;
    @Getter @Setter private PlayerSkin playerSkin;

    public NPCEntity(@NotNull UUID uuid, @NotNull String username) {
        super(EntityType.PLAYER, uuid);
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

}
