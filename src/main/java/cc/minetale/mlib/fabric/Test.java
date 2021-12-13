package cc.minetale.mlib.fabric;

import cc.minetale.mlib.fabric.inventory.FabricInventory;
import cc.minetale.mlib.fabric.inventory.FabricType;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class Test extends FabricInventory {

    public Test(Player player) {
        super(Component.text("Test Inventory"), FabricType.SIX_ROW);

        fill(ItemStack.of(Material.GRAY_STAINED_GLASS_PANE).withDisplayName(Component.empty()));

        addFragment(0, 0, 0, 0, ItemStack.of(Material.DIAMOND_BLOCK), false, (e) -> {});

        open(player);
    }

}
