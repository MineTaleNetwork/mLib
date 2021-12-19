package cc.minetale.mlib.canvas;

import lombok.Getter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.trait.PlayerEvent;
import net.minestom.server.timer.ExecutionType;
import net.minestom.server.utils.time.Tick;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MenuHandler {

    @Getter private static MenuHandler instance;
    private final Map<Player, Menu> menus = new ConcurrentHashMap<>();

    public MenuHandler() {
        instance = this;

        MinecraftServer.getGlobalEventHandler().addChild(eventNode());
        MinecraftServer.getSchedulerManager()
                .buildTask(() -> {
                    for (var menu : menus.values()) {
                        menu.tick();
                    }
                })
                .repeat(1, Tick.SERVER_TICKS)
                .executionType(ExecutionType.ASYNC)
                .schedule();
    }

    private EventNode<PlayerEvent> eventNode() {
        return EventNode.type("canvas", EventFilter.PLAYER)
                .addListener(InventoryPreClickEvent.class, event -> {
                    var player = event.getPlayer();
                    var menu = this.findMenu(player);

                    menu.ifPresent(value -> value.click(event));
                })
                .addListener(InventoryCloseEvent.class, event -> {
                    var player = event.getPlayer();
                    var menu = this.findMenu(player);

                    menu.ifPresent(value -> value.handleClose(player));
                })
                .addListener(PlayerDisconnectEvent.class, event -> {
                    var player = event.getPlayer();
                    var menu = this.findMenu(player);

                    menu.ifPresent(value -> value.handleClose(player));
                });
    }

    public void register(Player player, Menu menu) {
        this.menus.put(player, menu);
    }

    public void unregister(Player player) {
        this.menus.remove(player);
    }

    public Optional<Menu> findMenu(Player player) {
        return Optional.ofNullable(this.menus.getOrDefault(player, null));
    }

}
