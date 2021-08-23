package cc.minetale.mlib.mixin;

import cc.minetale.mlib.mLib;
import cc.minetale.mlib.npc.NPCInteractEvent;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventDispatcher;
import net.minestom.server.listener.UseEntityListener;
import net.minestom.server.network.packet.client.play.ClientInteractEntityPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UseEntityListener.class)
public class UseEntityListenerMixin {

    @Inject(at = @At("INVOKE"), method = "useEntityListener", remap = false)
    private static void useEntityListener(ClientInteractEntityPacket packet, Player player, CallbackInfo ci) {
//        if(packet.hand == Player.Hand.MAIN) {
            mLib.getMLib().getNpcList().stream().filter(npc -> npc.npcEntityId == packet.targetId).findFirst().ifPresent(npc -> {
                NPCInteractEvent npcInteractEvent = new NPCInteractEvent(player, npc);
                EventDispatcher.call(npcInteractEvent);
                player.sendMessage("You just interacted with npc " + npc.npcEntity.getUsername());
            });
//        }
    }

}
