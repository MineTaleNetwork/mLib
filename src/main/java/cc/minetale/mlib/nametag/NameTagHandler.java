package cc.minetale.mlib.nametag;

import net.minestom.server.entity.Player;
import net.minestom.server.scoreboard.Team;

import java.util.*;

public class NameTagHandler {

    // TODO Add to Flame
    private static Map<Player, TreeMap<Integer, Team>> nametagMap = new HashMap<>();

    public void reloadPlayer(Player player) {
        var nametagSet = nametagMap.get(player);

        if(nametagSet != null) {

        }
    }

    public void addTeam(Team team, int weight) {

    }

}
