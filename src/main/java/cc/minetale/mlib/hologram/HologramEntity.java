package cc.minetale.mlib.hologram;

import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.metadata.other.ArmorStandMeta;

public class HologramEntity extends Entity {

    public HologramEntity() {
        super(EntityType.ARMOR_STAND);

        ArmorStandMeta meta = (ArmorStandMeta) this.getEntityMeta();

        meta.setMarker(true);
        meta.setHasNoGravity(true);
        meta.setInvisible(true);
    }

}
