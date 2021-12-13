package cc.minetale.mlib.nametag;

import lombok.Getter;
import lombok.Setter;
import net.minestom.server.scoreboard.Team;

@Getter @Setter
public class NametagProvider {

    private Team team;
    private int weight;

    public NametagProvider(Team team, int weight) {
        this.team = team;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object object) {
        return this == object || object instanceof NametagProvider other && other.getTeam().getTeamName().equals(this.team.getTeamName());
    }

}
