package cc.minetale.mlib.canvas;

import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

@Getter @Setter
public abstract class Menu {

    private final Player player;
    private final Component title;
    private final CanvasType type;

    private final Fragment[] fragments;
    private FillingType filler;
    private ItemStack fillerType = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE).withDisplayName(Component.empty());

    private Pagination pagination;
    private Inventory inventory;
    private boolean readOnly = true;

    public Menu(Player player, Component title, CanvasType type) {
        this.player = player;
        this.title = title;
        this.type = type;

        this.fragments = new Fragment[this.type.getSize()];
        this.inventory = new Inventory(this.type.getType(), this.title);
    }

    public void setFragment(int slot, Fragment fragment) {
        this.fragments[slot] = fragment;
    }

    public Fragment[] getFillerFragments() {
        var fragments = new Fragment[this.type.getSize()];

        if(this.filler != null) {
            var fillers = filler.fillMenu(this);

            for (int i = 0; i < fillers.length; i++) {
                if (fillers[i] != null && this.fragments[i] == null) {
                    this.fragments[i] = fillers[i];
                }
            }
        }

        return fragments;
    }

    public void refresh() {
        this.updateTitle();
        this.setItems();
    }

    public void openMenu() {
        this.refresh();

        this.player.openInventory(this.inventory);

        this.registerMenu();
    }

    public void setItems() {
        this.clearMenu(this.inventory);

        var fillerFragments = this.getFillerFragments();

        for (int i = 0; i < fillerFragments.length; i++) {
            if (fillerFragments[i] != null) {
                this.fragments[i] = fillerFragments[i];
            }
        }

        if(this.pagination != null) {
            int fragmentIndex = 0;
            var fragments = this.pagination.getPageItems();

            for (int index = this.pagination.getStart(); index < this.getType().getSize() - 1; index++) {
                if (fragmentIndex < fragments.length) {
                    if(this.fragments[index] == null || this.fragments[index].itemStack() != this.fillerType) {
                        this.fragments[index] = fragments[fragmentIndex];
                        fragmentIndex++;
                    }
                }
            }
        }

        for(int index = 0; index < this.fragments.length; index++) {
            if (this.fragments[index] != null) {
                this.inventory.setItemStack(index, this.fragments[index].itemStack());
            }
        }
    }

    public abstract void close();

    public void registerMenu() {
        MenuHandler.register(this.player, this);
    }

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

        var fragment = this.fragments[event.getSlot()];

        if (fragment == null) {
            event.setCancelled(true);
            return;
        }

        var clickAction = fragment.clickAction();

        event.setCancelled(this.readOnly);

        if (clickAction != null) {
            clickAction.accept(event);
        }
    }

    public void handleClose(Player player) {
        this.close();
        MenuHandler.unregister(player);
    }

    public void updateTitle() {
        if(this.pagination != null) {
            this.inventory.setTitle(this.title.append(Component.text(" (" + (pagination.getCurrentPage() + 1) + "/" + pagination.getPageCount() + ")")));
        } else {
            this.inventory.setTitle(this.title);
        }
    }

}
