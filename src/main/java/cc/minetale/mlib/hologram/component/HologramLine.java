package cc.minetale.mlib.hologram.component;

import cc.minetale.mlib.hologram.HologramEntity;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.other.ArmorStandMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;

import java.util.List;
import java.util.Objects;

@Getter
public class HologramLine extends HologramComponent {

    private Component text;

    public HologramLine(Component text) {
        this.text = Objects.requireNonNullElseGet(text, Component::empty);
    }

    @Override
    public void tick() {
        for(Player player : this.getEntity().getViewers()) {
            this.update(player);
        }
    }

    @Override
    public void create(Instance instance, Pos position) {
        HologramEntity entity = new HologramEntity(this);

        ArmorStandMeta meta = (ArmorStandMeta) entity.getEntityMeta();

        meta.setNotifyAboutChanges(false);

        meta.setCustomName(this.text);
        meta.setCustomNameVisible(!this.text.equals(Component.empty()));

        meta.setNotifyAboutChanges(true);

        this.setEntity(entity);

        this.getEntity().setInstance(instance, position);
    }

    @Override
    public void update(Player player) {
        setText(this.text, player);
    }

    @Override
    public double getHeight() {
        return 0.27;
    }

    public void setText(Component text) {
        this.text = text;

        HologramEntity entity = this.getEntity();

        if(entity == null || entity.isRemoved()) { return; }

        ArmorStandMeta meta = (ArmorStandMeta) this.getEntity().getEntityMeta();

        meta.setNotifyAboutChanges(false);

        meta.setCustomName(text);

        meta.setNotifyAboutChanges(true);

        this.getEntity().getViewers().forEach(player -> setText(text, player));
    }

    public void setText(Component text, Player player) {
        HologramEntity entity = this.getEntity();

        if(entity == null || entity.isRemoved()) { return; }

        EntityMetaDataPacket packet = new EntityMetaDataPacket(this.getEntity().getEntityId(), List.of(
                new Metadata.Entry<>((byte) 2, Metadata.OptChat(text)
        )));

        player.sendPacket(packet);
    }

    public static HologramLine empty() {
        return new HologramLine(Component.text(" "));
    }

}