package cc.minetale.mlib.npc;

import cc.minetale.mlib.hologram.Hologram;
import cc.minetale.mlib.hologram.component.HologramComponent;
import cc.minetale.mlib.mLib;
import lombok.Getter;
import lombok.Setter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.*;
import net.minestom.server.entity.metadata.PlayerMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.EntityHeadLookPacket;
import net.minestom.server.network.packet.server.play.EntityRotationPacket;
import net.minestom.server.network.packet.server.play.PlayerInfoPacket;
import net.minestom.server.scoreboard.Team;
import net.minestom.server.utils.time.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.function.Consumer;

@Getter @Setter
public class NPC extends LivingEntity {

    private Pos position;
    private PlayerSkin playerSkin;
    private Hologram hologram;
    private String username;
    private Consumer<NPCInteraction> interaction;

    public NPC(@NotNull Instance instance, @NotNull Pos position, @NotNull PlayerSkin playerSkin, @NotNull Consumer<NPCInteraction> interaction, HologramComponent... components) {
        super(EntityType.PLAYER);

        this.position = position;
        this.playerSkin = playerSkin;
        this.interaction = interaction;

        this.hologram = new Hologram(instance, new Pos(position).add(0.0, 1.5, 0.0), false);

        for(HologramComponent component : components) {
            this.hologram.append(component);
        }

        this.username = RandomStringUtils.randomAlphanumeric(8);

        this.setNoGravity(true);

        PlayerMeta meta = new PlayerMeta(this, this.metadata);

        meta.setCapeEnabled(true);
        meta.setHatEnabled(true);
        meta.setJacketEnabled(true);
        meta.setLeftLegEnabled(true);
        meta.setLeftSleeveEnabled(true);
        meta.setRightLegEnabled(true);
        meta.setRightSleeveEnabled(true);

        this.entityMeta = meta;

        Team team = mLib.getNpcTeam();

        team.addMember(this.getUsername());
        this.setTeam(team);

        this.setAutoViewable(false);

        this.setInstance(instance, position);
        this.hologram.create();
    }

//    @Override
//    protected boolean addViewer0(@NotNull Player player) {
//        player.sendPacket(this.generateAddPlayer());
//
//        if (!super.addViewer0(player)) {
//            return false;
//        } else {
//            lookAt(player);
//
//            MinecraftServer.getSchedulerManager().buildTask(() -> player.sendPacket(generateRemovePlayer()))
//                    .delay(Duration.of(20, TimeUnit.SERVER_TICK))
//                    .schedule();
//
//            return true;
//        }
//    }

    @Override
    public void tick(long time) {
        super.tick(time);

        Instance instance = this.instance;

        if(this.isRemoved() || instance == null)
            return;

        for(Player player : instance.getPlayers()) {
            boolean canSee = player.getDistance(this) <= 32;

            if(this.viewers.contains(player) && !canSee) {
                this.removeViewer(player);
            } else if(canSee) {
                lookAt(player);

                if (!this.viewers.contains(player)) {
                    this.addViewer(player);
                }
            }
        }
    }

    public void lookAt(Player player) {
        Pos direction = this.position.withDirection(player.getPosition().sub(this.position));

        player.sendPacket(new EntityRotationPacket(this.getEntityId(), direction.yaw(), direction.pitch(), false));
        player.sendPacket(new EntityHeadLookPacket(this.getEntityId(), direction.yaw()));
    }

    public PlayerInfoPacket generateRemovePlayer() {
        PlayerInfoPacket packet = new PlayerInfoPacket(PlayerInfoPacket.Action.REMOVE_PLAYER);
        packet.playerInfos.add(new PlayerInfoPacket.RemovePlayer(this.getUuid()));

        return packet;
    }

    public PlayerInfoPacket generateAddPlayer() {
        PlayerInfoPacket packet = new PlayerInfoPacket(PlayerInfoPacket.Action.ADD_PLAYER);
        PlayerInfoPacket.AddPlayer playerInfo = new PlayerInfoPacket.AddPlayer(this.getUuid(), this.getUsername(), GameMode.CREATIVE, 0);

        playerInfo.properties.add(new PlayerInfoPacket.AddPlayer.Property("textures", this.getPlayerSkin().getTextures(), this.getPlayerSkin().getSignature()));
        packet.playerInfos.add(playerInfo);

        return packet;
    }

}
