package cc.minetale.mlib.fabric.event;

import cc.minetale.mlib.fabric.inventory.FabricInventory;
import lombok.Getter;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.click.ClickType;

@Getter
public class FragmentClickEvent extends Event {

    private final Player player;
    private final InventoryPreClickEvent event;
    private final FabricInventory fabricInventory;

    public FragmentClickEvent(InventoryPreClickEvent event, FabricInventory fabricInventory) {
        this.player = event.getPlayer();
        this.event = event;
        this.fabricInventory = fabricInventory;
    }

    public void cancel() {
        this.event.setCancelled(true);
    }

    public void setCancelled(boolean cancelled) {
        this.event.setCancelled(cancelled);
    }

    public int getSlot() {
        return this.event.getSlot();
    }

    public ClickType getClickType() {
        return this.event.getClickType();
    }

}
