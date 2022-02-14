package cc.minetale.mlib.canvas;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.trait.PlayerEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MenuHandler {

    private static final Map<Player, Menu> menus = Collections.synchronizedMap(new HashMap<>());

    public static void init() {
        MinecraftServer.getGlobalEventHandler().addChild(eventNode());
    }

    private static EventNode<PlayerEvent> eventNode() {
        return EventNode.type("canvas", EventFilter.PLAYER)
                .addListener(InventoryPreClickEvent.class, event -> {
                    var player = event.getPlayer();
                    var menu = findMenu(player);

                    menu.ifPresent(value -> value.click(event));
                })
                .addListener(InventoryCloseEvent.class, event -> {
                    var player = event.getPlayer();
                    var menu = findMenu(player);

                    menu.ifPresent(value -> value.handleClose(player));
                })
                .addListener(PlayerDisconnectEvent.class, event -> {
                    var player = event.getPlayer();
                    var menu = findMenu(player);

                    menu.ifPresent(value -> value.handleClose(player));
                });
    }

    public static void register(Player player, Menu menu) {
        menus.put(player, menu);
    }

    public static void unregister(Player player) {
        menus.remove(player);
    }

    public static Optional<Menu> findMenu(Player player) {
        return Optional.ofNullable(menus.getOrDefault(player, null));
    }

}
