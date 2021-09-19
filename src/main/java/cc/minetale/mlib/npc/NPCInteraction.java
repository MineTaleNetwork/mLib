package cc.minetale.mlib.npc;

import lombok.Getter;
import net.minestom.server.entity.Player;

@Getter
public class NPCInteraction {

    private final Player player;
    private final NPC npc;

    public NPCInteraction(Player player, NPC npc) {
        this.player = player;
        this.npc = npc;
    }

}
