package cc.minetale.mlib.nametag;

import net.minestom.server.scoreboard.Team;

public record NameplateProvider(Team team, ProviderType type) {

    @Override
    public boolean equals(Object object) {
        return this == object || object instanceof NameplateProvider other && other.team().getTeamName().equals(this.team.getTeamName());
    }

}
