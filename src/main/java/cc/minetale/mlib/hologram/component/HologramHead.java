package cc.minetale.mlib.hologram.component;

import cc.minetale.mlib.hologram.EntityHologram;
import lombok.Getter;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.other.ArmorStandMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.server.play.EntityEquipmentPacket;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Getter
public class HologramHead extends HologramComponent {

    private ItemStack itemStack;
    private final boolean small;

    public HologramHead(@NotNull ItemStack itemStack, boolean small) {
        this.itemStack = itemStack;
        this.small = small;
    }

    @Override
    public void create(Instance instance, Pos position) {
        var entity = new EntityHologram();

        var meta = (ArmorStandMeta) entity.getEntityMeta();

        entity.setHelmet(this.itemStack);
        meta.setSmall(this.small);

        this.setEntity(entity);

        entity.setInstance(instance, position);
    }

    @Override
    public double getHeight() {
        return this.small ? 0.48 : 0.7;
    }

    @Override
    public Vec getDescendingOffset() {
        return new Vec(0.0, this.small ? -0.6 : -1.5, 0.0);
    }

    @Override
    public Vec getAscendingOffset() {
        return new Vec(0.0, this.small ? -0.4 : -1.075, 0.0);
    }

    public void setHead(ItemStack itemStack) {
        this.itemStack = itemStack;

        this.getEntity().setHelmet(this.itemStack);
    }

    public void setHead(ItemStack itemStack, Player player) {
        player.sendPacket(new EntityEquipmentPacket(this.getEntity().getEntityId(), Map.of(EquipmentSlot.HELMET, itemStack)));
    }

    public static HologramHead of(ItemStack itemStack, boolean small) {
        return new HologramHead(itemStack, small);
    }

}
