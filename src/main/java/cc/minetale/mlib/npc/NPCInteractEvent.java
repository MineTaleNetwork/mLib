package cc.minetale.mlib.npc;

import net.minestom.server.entity.Player;
import net.minestom.server.event.trait.CancellableEvent;
import net.minestom.server.event.trait.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public class NPCInteractEvent implements PlayerEvent, CancellableEvent {

    private final Player player;
    private final NPC npc;
    private boolean cancelled;

    public NPCInteractEvent(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public @NotNull Player getPlayer() {
        return this.player;
    }

    public NPC getNpc() {
        return npc;
    }
}
