package cc.minetale.mlib.guilib.events;

import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.click.ClickType;

public class ButtonClickEvent {

    private final InventoryPreClickEvent event;

    public ButtonClickEvent(InventoryPreClickEvent event) {
        this.event = event;
    }

    /**
     * Get the player who clicked on the button
     *
     * @return the player who clicked the button
     */
    public Player getPlayer() {
        return event.getPlayer();
    }

    /**
     * Set if the event is cancelled
     * true = Player can't take the item
     * false = Player can take the item
     *
     * @param cancelled if the event is cancelled
     */
    public void setCancelled(boolean cancelled) {
        event.setCancelled(cancelled);
    }

    /**
     * Get the clicked inventory
     *
     * @return clicked inventory
     */
    public Inventory getInventory() {
        return event.getInventory();
    }

    /**
     * Returns true if the click was a right click
     *
     * @return if the click was a right click
     */
    public boolean isRightClick() {
        return event.getClickType() == ClickType.RIGHT_CLICK;
    }

    /**
     * Returns true if the click was performed using SHIFT
     *
     * @return if the click was performed with SHIFT
     */
    public boolean isShitClick() {
        return event.getClickType() == ClickType.SHIFT_CLICK;
    }

    /**
     * Returns true if the click was a left click
     *
     * @return if the click was a left click
     */
    public boolean isLeftClick() {
        return event.getClickType() == ClickType.LEFT_CLICK;
    }

    /**
     * Get the Minestom InventoryPreClickEvent
     *
     * @return minestoms InventoryPreClickEvent
     */
    public InventoryPreClickEvent getEvent() {
        return event;
    }
}