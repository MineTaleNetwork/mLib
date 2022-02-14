package cc.minetale.mlib.canvas;

import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class TestMenu extends Menu {

    public TestMenu() {
        super(Component.text("Friend Requests"), CanvasType.SIX_ROW);

        setFiller(Filler.BORDER);

        setButton(1, Fragment.empty(ItemStack.of(Material.DIAMOND)));

        setButton(2, Fragment.of(ItemStack.of(Material.DIAMOND), event -> {
            event.getPlayer().closeInventory();
        }));

        setCloseAction(player -> player.sendMessage("You closed the menu!"));
    }

}
