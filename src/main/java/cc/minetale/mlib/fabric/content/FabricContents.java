package cc.minetale.mlib.fabric.content;

import cc.minetale.mlib.fabric.ClickableItem;
import cc.minetale.mlib.fabric.FabricInventory;
import cc.minetale.mlib.fabric.ISlotIterator;
import lombok.Getter;
import lombok.Setter;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.ItemStack;

import java.util.Optional;
import java.util.UUID;

@Getter @Setter
public class FabricContents {

    private final FabricInventory fabric;
    private final UUID player;
    private final ClickableItem[] contents;
    private final Pagination pagination;

    public FabricContents(FabricInventory fabric, UUID player) {
        this.fabric = fabric;
        this.player = player;
        this.contents = new ClickableItem[this.fabric.getType().getSize()];
        this.pagination = new Pagination();
    }

    public Optional<ClickableItem> getSlot(int slot) {
        if (slot == -999)
            return Optional.empty();

        return Optional.ofNullable(this.contents[slot]);
    }

    public void setSlot(int slot, ClickableItem item) {
        if (item == null)
            return;

        this.contents[slot] = item;
        this.update(slot, item.getItem());
    }

    public ISlotIterator iterator(int startPos) {
        return new SlotIterator(this, this.fabric, startPos);
    }

    public void fill(ClickableItem item) {
        for (int slot = 0; slot < this.contents.length; slot++)
            this.setSlot(slot, item);
    }

    public void fill(ClickableItem item, int startPos, int endPos) {
        for (int slot = startPos; slot <= endPos; slot++)
            this.setSlot(slot, item);
    }

    private void update(int slot, ItemStack item) {
        Player currentPlayer = MinecraftServer.getConnectionManager().getPlayer(player);

        if (currentPlayer == null)
            return;

        if (!this.fabric.getManager().getOpenedPlayers(this.fabric).contains(currentPlayer))
            return;

        Inventory inventory = currentPlayer.getOpenInventory();

        if (inventory == null)
            return;

        inventory.setItemStack(slot, item);
    }
}
