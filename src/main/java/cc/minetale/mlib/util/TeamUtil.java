package cc.minetale.mlib.util;

import cc.minetale.commonlib.grant.Rank;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.network.packet.server.play.TeamsPacket;
import net.minestom.server.scoreboard.Team;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TeamUtil {

    public static final Team NPC_TEAM;
    public static final Map<Rank, Team> RANK_MAP = Collections.synchronizedMap(new HashMap<>());

    static {
        NPC_TEAM = MinecraftServer.getTeamManager().createTeam(
                "NPC-TEAM",
                Component.text("[NPC] ", NamedTextColor.DARK_GRAY),
                NamedTextColor.DARK_GRAY,
                Component.empty()
        );

        NPC_TEAM.setNameTagVisibility(TeamsPacket.NameTagVisibility.NEVER);

        for(Rank rank : Rank.values()) {
            var team = MinecraftServer.getTeamManager().createTeam(
                    rank.ordinal() + "-" + rank.getName(),
                    rank.getPrefix().append(Component.space()),
                    rank.getColor(),
                    Component.empty()
            );

            RANK_MAP.put(rank, team);
        }
    }

}
