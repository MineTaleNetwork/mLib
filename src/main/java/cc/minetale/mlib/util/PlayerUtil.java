package cc.minetale.mlib.util;

import net.minestom.server.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class PlayerUtil {

    public static List<String> getNames(Collection<Player> players) {
        return players.stream().map(Player::getUsername).collect(Collectors.toList());
    }

    public static List<UUID> getUUIDs(Collection<Player> players) {
        return players.stream().map(Player::getUuid).collect(Collectors.toList());
    }

    public static Map<UUID, String> getAll(Collection<Player> players) {
        return players.stream().collect(Collectors.toMap(Player::getUuid, Player::getUsername));
    }

}
