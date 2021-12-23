package cc.minetale.mlib.util;

import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.network.ConnectionManager;
import net.minestom.server.network.packet.server.play.PlayerInfoPacket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PacketUtil {

    public static PlayerInfoPacket addPlayerInfoPacket(UUID uuid, String username, PlayerSkin skin) {
        var textureProperty = new PlayerInfoPacket.AddPlayer.Property("textures", skin.textures(), skin.signature());
        var playerEntry = new PlayerInfoPacket.AddPlayer(uuid, username, Collections.singletonList(textureProperty), GameMode.CREATIVE, 0, null);

        return new PlayerInfoPacket(PlayerInfoPacket.Action.ADD_PLAYER, Collections.singletonList(playerEntry));
    }

    public static PlayerInfoPacket removePlayerInfoPacket(UUID uuid) {
        var playerEntry = new PlayerInfoPacket.RemovePlayer(uuid);

        return new PlayerInfoPacket(PlayerInfoPacket.Action.REMOVE_PLAYER, Collections.singletonList(playerEntry));
    }

    public static PlayerInfoPacket removeServerInfoPacket(List<UUID> playerUuids) {
        ArrayList<PlayerInfoPacket.Entry> entries = new ArrayList<>();

        for(var player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            var uuid = player.getUuid();

            if(!playerUuids.contains(uuid))
                entries.add(new PlayerInfoPacket.RemovePlayer(uuid));
        }

        return new PlayerInfoPacket(PlayerInfoPacket.Action.REMOVE_PLAYER, entries);
    }

}
