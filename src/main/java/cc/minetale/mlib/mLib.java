package cc.minetale.mlib;

import cc.minetale.commonlib.CommonLib;
import cc.minetale.mlib.canvas.MenuHandler;
import cc.minetale.mlib.nametag.NameplateHandler;
import cc.minetale.mlib.npc.NPC;
import cc.minetale.mlib.npc.NPCInteraction;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.PlayerEntityInteractEvent;
import net.minestom.server.event.trait.EntityEvent;
import net.minestom.server.extensions.Extension;

public class mLib extends Extension {

    @Override
    public void initialize() {
        MinecraftServer.getGlobalEventHandler().addChild(events());

        MenuHandler.init();
        NameplateHandler.init();
        CommonLib.init();
    }

    @Override
    public void terminate() {}

    public static EventNode<EntityEvent> events() {
        return EventNode.type("mLib", EventFilter.ENTITY)
                .addListener(PlayerEntityInteractEvent.class, event -> {
                    var target = event.getTarget();

                    if (!(target instanceof NPC npc)) return;

                    if(event.getHand() == Player.Hand.MAIN)
                        npc.getInteraction().accept(new NPCInteraction(event.getPlayer(), npc));
                });
    }

}
