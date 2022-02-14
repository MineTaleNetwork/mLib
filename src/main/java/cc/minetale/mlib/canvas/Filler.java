package cc.minetale.mlib.canvas;

import net.kyori.adventure.text.Component;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

public enum Filler {
    BORDER {
        @Override
        public void fillMenu(Fragment[] fragments, ItemStack item) {
            var size = fragments.length;

            for (int i = 0; i < size; i++) {
                if ((i < 9 || i >= size - 9 || i % 9 == 0 || i % 9 == 8) && fragments[i] == null) {
                    fragments[i] = new Fragment(item, event -> event.setCancelled(true));
                }
            }
        }
    },

    EMPTY_SLOTS {
        @Override
        public void fillMenu(Fragment[] fragments, ItemStack item) {
            var size = fragments.length;

            for (int i = 0; (i < size) && fragments[i] == null; i++) {
                fragments[i] = new Fragment(item, event -> event.setCancelled(true));
            }
        }
    },

    TOP_BOTTOM {
        @Override
        public void fillMenu(Fragment[] fragments, ItemStack item) {
            var size = fragments.length;

            for (int i = 0; i < size; i++) {
                if((i < 9 || i > size - 10) && fragments[i] == null) {
                    fragments[i] = new Fragment(item, event -> event.setCancelled(true));
                }
            }
        }
    };

    public abstract void fillMenu(Fragment[] fragments, ItemStack item);

    public static final ItemStack DEFAULT = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE).withDisplayName(Component.empty());

}
