package cc.minetale.mlib.nametag;

import net.minestom.server.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NameTagHandler {

    private static Map<Player, Set<NametagProvider>> nametagMap = new HashMap<>();

    public void reloadPlayer(Player player) {
        var nametagSet = nametagMap.get(player);

        if(nametagSet != null) {

        }
    }

}
