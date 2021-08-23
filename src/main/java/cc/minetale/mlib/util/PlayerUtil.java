package cc.minetale.mlib.util;

import net.minestom.server.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class PlayerUtil {

    public static List<String> getNames(List<Player> players) {
        return players.stream().map(Player::getUsername).collect(Collectors.toList());
    }

    public static List<UUID> getUUIDs(List<Player> players) {
        return players.stream().map(Player::getUuid).collect(Collectors.toList());
    }

    public static Map<UUID, String> getAll(List<Player> players) {
        return players.stream().collect(Collectors.toMap(Player::getUuid, Player::getUsername));
    }

}
