package cc.minetale.mlib.oldfabric;

import cc.minetale.mlib.fabric.inventory.FabricContents;
import cc.minetale.mlib.fabric.inventory.FabricProvider;
import cc.minetale.mlib.mLib;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;

import java.util.Optional;

@Getter
public class FabricInventory {

    private Component title;
    private InventoryType type;
    private Inventory inventory;
    private FabricProvider provider;
    private boolean readOnly;

    private final FabricManager manager;

    private FabricInventory(FabricManager manager) {
        this.manager = manager;
    }

    public void open(Player player) {
        open(player, 0);
    }

    public void open(Player player, int page) {
        FabricContents contents = new FabricContents(this, player.getUuid());

        contents.getPagination().page(page);

        this.manager.setContents(player, contents);

        try {
            this.provider.init(player, contents);

            if (!this.manager.getContents(player).equals(Optional.of(contents)))
                return;

            this.inventory = this.openInventory(player);
        } catch (Exception e) {
            this.manager.handleInventoryOpenError(this, player, e);
        }
    }

    public void close(Player player) {
        player.closeInventory();

        this.manager.setInventory(player, null);
        this.manager.setContents(player, null);
    }

    private Inventory openInventory(Player player) {
        Inventory handle = new Inventory(this.getType(), this.getTitle());

        fill(handle, this.manager.getContents(player).get());

        this.manager.setInventory(player, this);

        player.openInventory(handle);

        return handle;
    }

    private void fill(Inventory handle, FabricContents contents) {
        ClickableItem[] items = contents.getContents();

        for(int slot = 0; slot < items.length; slot++) {
            if(items[slot] != null)
                handle.setItemStack(slot, items[slot].getItem());
        }
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Component title = Component.empty();
        private InventoryType type = InventoryType.CHEST_3_ROW;
        private boolean readOnly = true;

        private FabricManager manager;
        private FabricProvider provider;

        private Builder() {}

        public Builder title(Component title) {
            this.title = title;
            return this;
        }

        public Builder type(InventoryType type) {
            this.type = type;
            return this;
        }

        public Builder readOnly(boolean readOnly) {
            this.readOnly = readOnly;
            return this;
        }

        public Builder provider(FabricProvider provider) {
            this.provider = provider;
            return this;
        }

        public Builder manager(FabricManager manager) {
            this.manager = manager;
            return this;
        }

        public FabricInventory build() {
            if(this.provider == null)
                throw new IllegalStateException("The provider of the Builder must be set.");

            FabricManager manager = this.manager != null ? this.manager : mLib.getMLib().getFabric().getFabricManager();

            if(manager == null)
                throw new IllegalStateException("The manager of the Builder must be set.");

            FabricInventory fabric = new FabricInventory(manager);

            fabric.title = this.title;
            fabric.type = this.type;
            fabric.readOnly = this.readOnly;
            fabric.provider = this.provider;

            return fabric;
        }
    }

}
