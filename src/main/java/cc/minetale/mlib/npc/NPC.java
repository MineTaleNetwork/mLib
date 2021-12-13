package cc.minetale.mlib.npc;

import cc.minetale.mlib.hologram.component.HologramComponent;
import cc.minetale.mlib.mLib;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.util.TriState;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.*;
import net.minestom.server.entity.metadata.PlayerMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.EntityHeadLookPacket;
import net.minestom.server.network.packet.server.play.EntityRotationPacket;
import net.minestom.server.network.packet.server.play.PlayerInfoPacket;
import net.minestom.server.utils.time.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.Collections;
import java.util.function.Consumer;

@Getter @Setter
public class NPC extends LivingEntity {

    private Pos position;
    private PlayerSkin playerSkin;
    private TriState faceNearestPlayer;
    private Consumer<NPCInteraction> interaction;
    private HologramComponent[] holograms;

    private String username;

    private final PlayerInfoPacket removePlayerPacket;
    private final PlayerInfoPacket addPlayerPacket;

    public NPC(Instance instance, Pos position, PlayerSkin playerSkin, TriState faceNearestPlayer, Consumer<NPCInteraction> interaction, HologramComponent... components) {
        super(EntityType.PLAYER);

        this.position = position;
        this.playerSkin = playerSkin;
        this.faceNearestPlayer = faceNearestPlayer;
        this.interaction = interaction;

        this.username = RandomStringUtils.randomAlphanumeric(8);

        this.removePlayerPacket = new PlayerInfoPacket(
                PlayerInfoPacket.Action.REMOVE_PLAYER,
                Collections.singletonList(
                        new PlayerInfoPacket.RemovePlayer(this.getUuid())
                )
        );

        this.addPlayerPacket = new PlayerInfoPacket(
                PlayerInfoPacket.Action.ADD_PLAYER,
                Collections.singletonList(
                        new PlayerInfoPacket.AddPlayer(
                                this.getUuid(),
                                this.getUsername(),
                                Collections.singletonList(
                                        new PlayerInfoPacket.AddPlayer.Property(
                                                "textures",
                                                this.getPlayerSkin().textures(),
                                                this.getPlayerSkin().signature()
                                        )
                                ),
                                GameMode.CREATIVE,
                                0,
                                null
                        )
                )
        );

        this.setNoGravity(true);

        var meta = new PlayerMeta(this, this.metadata);

        meta.setCapeEnabled(true);
        meta.setHatEnabled(true);
        meta.setJacketEnabled(true);
        meta.setLeftLegEnabled(true);
        meta.setLeftSleeveEnabled(true);
        meta.setRightLegEnabled(true);
        meta.setRightSleeveEnabled(true);

        var team = mLib.getNpcTeam();
        team.addMember(this.getUsername());
        this.setTeam(team);

        this.setInstance(instance, position);
    }

    @Override
    public void updateNewViewer(@NotNull Player player) {
        var connection = player.getPlayerConnection();

        connection.sendPacket(this.addPlayerPacket);

        if(this.faceNearestPlayer == TriState.FALSE) {
            this.swingMainHand();
        }

        MinecraftServer.getSchedulerManager().buildTask(() -> player.sendPacket(this.removePlayerPacket))
                .delay(Duration.of(100, TimeUnit.SERVER_TICK))
                .schedule();

        super.updateNewViewer(player);
    }

    @Override
    public void updateOldViewer(@NotNull Player player) {
        var connection = player.getPlayerConnection();

        connection.sendPacket(this.removePlayerPacket);

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