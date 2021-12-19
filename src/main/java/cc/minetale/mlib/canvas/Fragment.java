package cc.minetale.mlib.canvas;

import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.item.ItemStack;

import java.util.function.Consumer;

public record Fragment(ItemStack itemStack, Consumer<InventoryPreClickEvent> clickAction) {

    public static Fragment of(ItemStack item, Consumer<InventoryPreClickEvent> consumer) {
        return new Fragment(item, consumer);
    }

    public static Fragment empty(ItemStack item) {
        return of(item, event -> event.setCancelled(true));
    }

}
