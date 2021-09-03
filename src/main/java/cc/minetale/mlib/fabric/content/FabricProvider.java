package cc.minetale.mlib.fabric.content;

import net.minestom.server.entity.Player;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryOpenEvent;

public interface FabricProvider {

    void init(Player player, FabricContents contents);
    default void update(Player player, FabricContents contents) {}
    default void open(InventoryOpenEvent event, FabricContents contents) {}
    default void close(InventoryCloseEvent event) {}

}
