package cc.minetale.mlib.canvas;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.ItemStack;

import java.util.function.Consumer;

@Getter
@Setter
public abstract class Menu {

    private final Component title;
    private final CanvasType type;

    private final Fragment[] buttons;
    private Filler filler;

    private Consumer<Player> openAction = player -> {};
    private Consumer<Player> closeAction = player -> {};
    private Consumer<Player> updateAction = player -> {};

    private boolean readOnly = true;
    private ItemStack fillerType = Filler.DEFAULT;
    private Inventory inventory;

    public Menu(Component title, CanvasType type) {
        this.title = title;
        this.type = type;

        this.buttons = new Fragment[type.getSize()];
        this.inventory = new Inventory(type.getType(), title);
    }

    public void setButton(int slot, Fragment fragment) {
        this.buttons[slot] = fragment;
        this.inventory.setItemStack(slot, fragment.itemStack());
    }

    public void openMenu(Player player) {
        setItems();
        player.openInventory(inventory);
        MenuHandler.register(player, this);
    }

    public void setItems() {
        this.clearMenu(this.inventory);

        var fragments = new Fragment[type.getSize()];

        // Fill background
        if(filler != null) {
            filler.fillMenu(fragments, fillerType);
        }

        // Overlay buttons on background
        for(int i = 0; i < type.getSize(); i++) {
            if(buttons[i] != null) {
                fragments[i] = buttons[i];
            }
        }

        // Set inventory
        for(int i = 0; i < fragments.length; i++) {
            if (fragments[i] != null) {
                inventory.setItemStack(i, fragments[i].itemStack());
            }
        }
    }

    public void close() {}

    public void clearMenu(Inventory inventory) {
        if(inventory != null) {
            for(int i = 0; i < this.type.getSize() - 1; i++) {
                inventory.setItemStack(i, ItemStack.AIR);
            }
        }
    }

    public void click(InventoryPreClickEvent event) {
        if(event.getSlot() > getType().getSize() - 1 || event.getSlot() < 0) {
            event.setCancelled(true);
            return;
        }

        var fragment = buttons[event.getSlot()];

        if (fragment == null) {
            event.setCancelled(true);
            return;
        }

        event.setCancelled(this.readOnly);

        var clickAction = fragment.clickAction();

        if (clickAction != null) { clickAction.accept(event); }
    }

    public void handleClose(Player player) {
        this.close();

        MenuHandler.unregister(player);
    }

    public static void open(Menu menu, Player player) {
        menu.openMenu(player);
    }

}
