package cc.minetale.mlib.hologram.component;

import cc.minetale.mlib.hologram.EntityHologram;
import cc.minetale.mlib.hologram.EntityItem;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Metadata;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.item.ItemEntityMeta;
import net.minestom.server.instance.Instance;
import net.minestom.server.item.ItemStack;
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HologramItem extends HologramComponent {

    private final ItemStack itemStack;
    private EntityItem entityItem;

    public HologramItem(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    @Override
    public void create(Instance instance, Pos position) {
        var entity = new EntityHologram();

        this.setEntity(entity);
        entity.setInstance(instance, position);

        var itemEntity = new EntityItem(this.itemStack);
        this.entityItem = itemEntity;

        entity.addPassenger(itemEntity);
    }

    @Override
    public double getHeight() {
        return 0.73;
    }

    @Override
    public Vec getDescendingOffset() {
        return new Vec(0, -0.25, 0);
    }

    @Override
    public Vec getAscendingOffset() {
        return new Vec(0, 0.23, 0);
    }

    public void setItemStack(ItemStack itemStack) {
        var meta = (ItemEntityMeta) this.entityItem.getEntityMeta();

        meta.setItem(itemStack);
    }

    public void setHead(ItemStack itemStack, Player player) {
        var entry = new Metadata.Entry<>((byte) 8, Metadata.Slot(itemStack));

        player.sendPacket(new EntityMetaDataPacket(this.getEntity().getEntityId(), List.of(entry)));
    }

    public static HologramItem of(ItemStack itemStack) {
        return new HologramItem(itemStack);
    }

}
