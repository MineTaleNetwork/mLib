package cc.minetale.mlib.guilib.buttons;

import cc.minetale.mlib.guilib.events.ClickEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class CloseButton extends Button {

    private ItemStack item;

    /**
     * A Button that closes the Inventory when you click on it
     */
    public CloseButton() {
    }

    /**
     * A Button that closes the Inventory when you click on it
     *
     * @param item the item for the button that will show up in the inventory
     */
    public CloseButton(ItemStack item) {
        this.item = item;
    }

    @Override
    public ItemStack getItem() {
        if (item != null) return item;
        return ItemStack.builder(Material.BARRIER)
                .displayName(Component.text("Exit", NamedTextColor.RED))
                .lore(Component.text("Exit the GUI", NamedTextColor.GRAY))
                .build();
    }

    @Override
    public ClickEvent getEvent() {
        return e -> e.getPlayer().closeInventory();
    }

}
