package cc.minetale.mlib.hologram;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.item.ItemEntityMeta;
import net.minestom.server.item.ItemStack;

public class EntityItem extends Entity {

    public EntityItem(ItemStack itemStack) {
        super(EntityType.ITEM);

        var meta = (ItemEntityMeta) this.getEntityMeta();

        meta.setItem(itemStack);
        this.setNoGravity(true);
    }

}
