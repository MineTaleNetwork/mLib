package cc.minetale.mlib.hologram;

import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.LivingEntity;
import net.minestom.server.entity.metadata.other.ArmorStandMeta;

public class EntityHologram extends LivingEntity {

    public EntityHologram() {
        super(EntityType.ARMOR_STAND);

        var meta = (ArmorStandMeta) this.getEntityMeta();

        meta.setNotifyAboutChanges(false);

        meta.setMarker(true);
        meta.setHasNoGravity(true);
        meta.setInvisible(true);

        meta.setNotifyAboutChanges(true);
    }

}
