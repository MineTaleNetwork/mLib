package cc.minetale.mlib.hologram.component;

import cc.minetale.mlib.hologram.EntityHologram;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.other.ArmorStandMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
public class HologramLine extends HologramComponent {

    private Component text;

    public HologramLine(@NotNull Component text) {
        this.text = text;
    }

    @Override
    public void create(Instance instance, Pos position) {
        EntityHologram entity = new EntityHologram();

        ArmorStandMeta meta = (ArmorStandMeta) entity.getEntityMeta();

        meta.setNotifyAboutChanges(false);

        meta.setCustomName(this.text);
        meta.setCustomNameVisible(!this.text.equals(Component.text(" ")));

        meta.setNotifyAboutChanges(true);

        this.setEntity(entity);

        entity.setInstance(instance, position);
    }

    @Override
    public double getHeight() {
        return 0.27;
    }

    @Override
    public Vec getDescendingOffset() {
        return Vec.ZERO;
    }

    @Override
    public Vec getAscendingOffset() {
        return Vec.ZERO;
    }

    public void setText(Component text) {
        this.text = text;

        ArmorStandMeta meta = (ArmorStandMeta) this.getEntity().getEntityMeta();

        meta.setCustomName(text);
    }

    public void setText(Component text, Player player) {
        var entry = new Metadata.Entry<>((byte) 2, Metadata.OptChat(text));

        player.sendPacket(new EntityMetaDataPacket(this.getEntity().getEntityId(), List.of(entry)));
    }

    public static HologramLine of(Component component) {
        return new HologramLine(component);
    }

    public static HologramLine empty() {
        return new HologramLine(Component.text(" "));
    }

}
