package cc.minetale.mlib.guilib.buttons;

import cc.minetale.mlib.guilib.events.ClickEvent;
import net.minestom.server.item.ItemStack;

public class ActionButton extends Button {

    private ItemStack item;
    private ClickEvent event;

    /**
     * A Button that performs an action when you click on it
     *
     * @param item  the item for the button that will show up in the inventory
     * @param event the event that will trigger when the button is clicked
     */
    public ActionButton(ItemStack item, ClickEvent event) {
        this.item = item;
        this.event = event;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public ClickEvent getEvent() {
        return event;
    }

}
