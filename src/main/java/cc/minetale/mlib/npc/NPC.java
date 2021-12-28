package cc.minetale.mlib.npc;

import cc.minetale.mlib.hologram.Hologram;
import cc.minetale.mlib.hologram.HologramDirection;
import cc.minetale.mlib.hologram.component.HologramComponent;
import cc.minetale.mlib.util.PacketUtil;
import cc.minetale.mlib.util.TeamUtil;
import lombok.Getter;
import net.kyori.adventure.util.TriState;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.entity.metadata.PlayerMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.EntityHeadLookPacket;
import net.minestom.server.network.packet.server.play.EntityRotationPacket;
import net.minestom.server.network.packet.server.play.PlayerInfoPacket;
import net.minestom.server.utils.time.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.function.Consumer;

@Getter
public class NPC extends LivingEntity {

    private final Pos position;
    private final PlayerSkin playerSkin;
    private final TriState faceNearestPlayer;
    private final Consumer<NPCInteraction> interaction;
    private final HologramComponent[] hologramComponents;
    private final Hologram hologram;

    private final String username;

    private final PlayerInfoPacket addPlayerInfoPacket;
    private final PlayerInfoPacket removePlayerInfoPacket;

    public NPC(Instance instance, Pos position, PlayerSkin playerSkin, TriState faceNearestPlayer, Consumer<NPCInteraction> interaction, HologramComponent... hologramComponents) {
        super(EntityType.PLAYER);

        this.position = position;
        this.playerSkin = playerSkin;
        this.faceNearestPlayer = faceNearestPlayer;
        this.interaction = interaction;
        this.hologramComponents = hologramComponents;

        this.username = RandomStringUtils.randomAlphanumeric(8);

        this.addPlayerInfoPacket = PacketUtil.addPlayerInfoPacket(this.uuid, this.username, this.playerSkin);
        this.removePlayerInfoPacket = PacketUtil.removePlayerInfoPacket(this.uuid);

        this.setNoGravity(true);

        var meta = new PlayerMeta(this, this.metadata);

        meta.setCapeEnabled(true);
        meta.setHatEnabled(true);
        meta.setJacketEnabled(true);
        meta.setLeftLegEnabled(true);
        meta.setLeftSleeveEnabled(true);
        meta.setRightLegEnabled(true);
        meta.setRightSleeveEnabled(true);

        this.hologram = new Hologram(instance, position.add(0.0, 1.7, 0.0), HologramDirection.ASCENDING).append(hologramComponents);
        this.hologram.create();

        var team = TeamUtil.NPC_TEAM;
        team.addMember(this.username);
        this.setTeam(team);

        this.setInstance(instance, position);
    }

    @Override
    public void updateNewViewer(@NotNull Player player) {
        var connection = player.getPlayerConnection();

        connection.sendPacket(this.addPlayerInfoPacket);

        if(this.faceNearestPlayer == TriState.FALSE) {
            this.swingMainHand();
        }

        MinecraftServer.getSchedulerManager().buildTask(() -> player.sendPacket(this.removePlayerInfoPacket))
                .delay(Duration.of(100, TimeUnit.SERVER_TICK))
                .schedule();

        super.updateNewViewer(player);
    }

    @Override
    public void updateOldViewer(@NotNull Player player) {
        var connection = player.getPlayerConnection();

        connection.sendPacket(this.removePlayerInfoPacket);

        super.updateOldViewer(player);
    }

    @Override
    public void tick(long time) {
        super.tick(time);

        if(this.faceNearestPlayer == TriState.TRUE) {
            for(var player : this.viewers) {
                this.lookAt(player);
            }
        }
    }

    public void lookAt(Player player) {
        var direction = this.position.withDirection(player.getPosition().sub(this.position));

        player.sendPacket(new EntityRotationPacket(this.getEntityId(), direction.yaw(), direction.pitch(), false));
        player.sendPacket(new EntityHeadLookPacket(this.getEntityId(), direction.yaw()));
    }

}