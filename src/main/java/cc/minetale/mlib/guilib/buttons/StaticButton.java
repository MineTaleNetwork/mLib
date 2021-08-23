package cc.minetale.mlib.guilib.buttons;

import cc.minetale.mlib.guilib.events.ClickEvent;
import net.minestom.server.item.ItemStack;

public class StaticButton extends Button {

    private ItemStack item;

    /**
     * A Button that does nothing when you click on it
     *
     * @param item the item for the button that will show up in the inventory
     */
    public StaticButton(ItemStack item) {
        this.item = item;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public ClickEvent getEvent() {
        return null;
    }

}
