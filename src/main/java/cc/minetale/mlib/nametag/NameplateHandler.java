package cc.minetale.mlib.nametag;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.trait.PlayerEvent;
import net.minestom.server.scoreboard.Team;

import java.util.*;

public class NameplateHandler {

    private static final Map<UUID, TreeMap<Integer, Team>> nameplateMap = new HashMap<>();

    public NameplateHandler() {
        MinecraftServer.getGlobalEventHandler().addChild(eventNode());
    }

    public static void reloadPlayer(Player player) {
        if(!nameplateMap.containsKey(player.getUuid())) {
            player.setTeam(null);
            return;
        }

        var treeMap = nameplateMap.get(player.getUuid());
        var team = treeMap.lastEntry().getValue();

        player.setTeam(team);
    }

    public static void addProvider(Player player, NameplateProvider provider) {
        var uuid = player.getUuid();
        var treeMap = new TreeMap<Integer, Team>();

        if(nameplateMap.containsKey(uuid)) {
            treeMap = nameplateMap.get(uuid);
        }

        treeMap.put(provider.type().ordinal(), provider.team());
        nameplateMap.put(uuid, treeMap);

        reloadPlayer(player);
    }

    public static void removeProvider(Player player, ProviderType type) {
        var uuid = player.getUuid();

        if(nameplateMap.containsKey(uuid)) {
            var treeMap = nameplateMap.get(uuid);

            treeMap.remove(type.ordinal());

            if(treeMap.isEmpty()) {
                nameplateMap.remove(uuid);
            } else {
                nameplateMap.put(uuid, treeMap);
            }
        }

        reloadPlayer(player);
    }

    public static void clearProviders(Player player) {
        var uuid = player.getUuid();

        if(nameplateMap.containsKey(uuid)) {
            nameplateMap.get(uuid).clear();
        }

        reloadPlayer(player);
    }

    public static void handleDisconnect(Player player) {
        nameplateMap.remove(player.getUuid());
    }

    private EventNode<PlayerEvent> eventNode() {
        return EventNode.type("nameplate", EventFilter.PLAYER)
                .addListener(PlayerDisconnectEvent.class, event -> nameplateMap.remove(event.getPlayer().getUuid()));
    }

}
