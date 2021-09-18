package cc.minetale.mlib.npc;

import cc.minetale.mlib.mLib;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class NPCTask {

    public static void startTask() {
        MinecraftServer.getSchedulerManager().buildTask(() -> {
            mLib npcs = mLib.getMLib();
            if (npcs.getNpcList().isEmpty()) return;
            for (NPC npc : npcs.getNpcList()) {
                for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                    boolean canSeeNPC = player.getInstance() == npc.instance && player.getPosition().distance(npc.pos) <= 32;
                    if (npc.viewers.contains(player) && !canSeeNPC) {
                        npc.delete(player);
                    } else if (canSeeNPC) {
                        if (!npc.viewers.contains(player)) {
                            try {
                                npc.spawn(player);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        npc.lookAt(player);
                    }
                }
            }
        }).repeat(Duration.of(1, ChronoUnit.SECONDS)).schedule();
    }

}
