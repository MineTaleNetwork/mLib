package cc.minetale.mlib.npc;

import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.*;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.*;
import net.minestom.server.utils.PacketUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class NPC {

    public Instance instance;
    public Pos pos;
    public NPCEntity npcEntity;
    public HashSet<Player> viewers;
    public int npcEntityId;

    public NPC(Instance instance, Pos pos, PlayerSkin playerSkin) {
        this.instance = instance;
        this.pos = pos;
        this.viewers = new HashSet<>();

        this.npcEntity = new NPCEntity(UUID.randomUUID(), RandomStringUtils.randomAlphanumeric(8).toLowerCase());

        this.npcEntity.setInstance(instance);
        this.npcEntityId = this.npcEntity.getEntityId();
        this.npcEntity.setNoGravity(true);
        this.npcEntity.setPlayerSkin(playerSkin);
    }

    public void spawn(Player player) {
        this.viewers.add(player);

        this.setPos(this.pos);

        PlayerInfoPacket addPlayerInfoPacket = new PlayerInfoPacket(PlayerInfoPacket.Action.ADD_PLAYER);

        PlayerInfoPacket.AddPlayer addPlayer = new PlayerInfoPacket.AddPlayer(npcEntity.getUuid(), npcEntity.getUsername(), GameMode.ADVENTURE, 0);
        PlayerInfoPacket.AddPlayer.Property property = new PlayerInfoPacket.AddPlayer.Property("textures", npcEntity.getPlayerSkin().getTextures(), npcEntity.getPlayerSkin().getSignature());

        addPlayer.properties.add(property);

        addPlayerInfoPacket.playerInfos.add(addPlayer);

        SpawnPlayerPacket spawnPlayerPacket = new SpawnPlayerPacket();

        spawnPlayerPacket.entityId = npcEntityId;
        spawnPlayerPacket.playerUuid = npcEntity.getUuid();
        spawnPlayerPacket.position = this.pos;

        PacketUtils.sendPacket(player, addPlayerInfoPacket);
        PacketUtils.sendPacket(player, spawnPlayerPacket);

        Collection<Metadata.Entry<?>> entries = List.of(
                new Metadata.Entry<>((byte) 17, Metadata.Byte((byte) 127))
        );

        PacketUtils.sendPacket(player, new EntityMetaDataPacket(this.npcEntityId, entries));

        lookAt(player);

        MinecraftServer.getSchedulerManager().buildTask(() -> {
            PlayerInfoPacket removePlayerInfoPacket = new PlayerInfoPacket(PlayerInfoPacket.Action.REMOVE_PLAYER);

            PlayerInfoPacket.RemovePlayer removePlayer = new PlayerInfoPacket.RemovePlayer(this.npcEntity.getUuid());
            removePlayerInfoPacket.playerInfos.add(removePlayer);

            PacketUtils.sendPacket(player, removePlayerInfoPacket);
        }).delay(Duration.of(2, ChronoUnit.SECONDS)).schedule();
    }

    public void delete(Player player) {
        this.viewers.remove(player);

        PacketUtils.sendPacket(player, new DestroyEntitiesPacket(this.npcEntityId));
    }

    public void setPos(Pos pos) {
        this.npcEntity.teleport(pos);

        updatePos(pos, true);
    }

    private void updatePos(Pos pos, boolean onGround) {
        PacketUtils.sendPacket(Audiences.players(), new EntityTeleportPacket(this.npcEntityId, pos, onGround));
    }

    public void lookAt(Player target) {
        Pos direction = this.pos.withDirection(target.getPosition().sub(this.pos));

        PacketUtils.sendPacket(target, new EntityRotationPacket(this.npcEntityId, direction.yaw(), direction.pitch(), false));
        PacketUtils.sendPacket(target, new EntityHeadLookPacket(this.npcEntityId, direction.yaw()));
    }
}
