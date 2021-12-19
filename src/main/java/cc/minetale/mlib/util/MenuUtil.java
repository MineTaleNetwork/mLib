package cc.minetale.mlib.util;

import cc.minetale.mlib.canvas.Fragment;
import cc.minetale.mlib.canvas.Menu;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public class MenuUtil {

    public static Fragment PREVIOUS_PAGE(Menu menu) {
        return Fragment.of(ItemStack.of(Material.GRAY_CARPET)
                .withDisplayName(Component.text("Previous Page", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC.as(false)))), event -> {
            var pagination = menu.getPagination();
            var player = menu.getPlayer();

            if(pagination.previousPage()) {
                menu.setItems();
                menu.updateTitle();
                menu.getInventory().update(player);

                SoundsUtil.playClickSound(player);
            } else {
                SoundsUtil.playErrorSound(player);
            }
        });
    }

    public static Fragment NEXT_PAGE(Menu menu) {
        return Fragment.of(ItemStack.of(Material.GRAY_CARPET)
                .withDisplayName(Component.text("Next Page", Style.style(NamedTextColor.GRAY, TextDecoration.ITALIC.as(false)))), event -> {
            var pagination = menu.getPagination();
            var player = menu.getPlayer();

            if(pagination.nextPage()) {
                menu.setItems();
                menu.updateTitle();
                menu.getInventory().update(player);

                SoundsUtil.playClickSound(player);
            } else {
                SoundsUtil.playErrorSound(player);
            }
        });
    }

}
