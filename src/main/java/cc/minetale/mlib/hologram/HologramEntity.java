package cc.minetale.mlib.hologram;

import cc.minetale.mlib.hologram.component.HologramComponent;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.EntityType;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.metadata.other.ArmorStandMeta;
import net.minestom.server.instance.Instance;
public class HologramEntity extends Entity {

    private HologramComponent component;

    public HologramEntity(HologramComponent component) {
        super(EntityType.ARMOR_STAND);

        this.component = component;

        ArmorStandMeta meta = (ArmorStandMeta) this.getEntityMeta();

        meta.setNotifyAboutChanges(false);

        meta.setMarker(true);

        meta.setHasNoGravity(true);
        meta.setInvisible(true);

        meta.setNotifyAboutChanges(true);

        this.setAutoViewable(false);
    }

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
            } else if(canSee && !this.viewers.contains(player)) {
                this.addViewer(player);
            }
        }

//        this.component.tick();
    }

}
