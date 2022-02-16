package cc.minetale.mlib.canvas.template;

import cc.minetale.commonlib.util.Pagination;
import cc.minetale.mlib.canvas.CanvasType;
import cc.minetale.mlib.canvas.Filler;
import cc.minetale.mlib.canvas.Fragment;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

@Getter
public class PaginatedMenu extends Menu {

    private final Pagination<Fragment> pagination;

    public PaginatedMenu(Player player, Component title, CanvasType type, Fragment[] fragments) {
        super(player, title, type);

        if(type.getSize() < 27) {
            type = CanvasType.THREE_ROW;
        }

        var size = type.getSize();

        pagination = new Pagination<>((size / 9 - 2) * 7, fragments);

        setFiller(Filler.BORDER);

        setButton(size - 6, Fragment.of(ItemStack.of(Material.GRAY_CARPET), event -> {}));
        setButton(size - 5, Fragment.of(ItemStack.of(Material.ARROW), event -> {}));
        setButton(size - 4, Fragment.of(ItemStack.of(Material.GRAY_CARPET), event -> {}));

        setPaginatedItems();
    }

    public void setPaginatedItems() {
        var fragments = pagination.getPageItems();
        var size = getType().getSize();

        for (int i = 0; i < size; i++) {
            if (i > 9 && i <= size - 9 && i % 9 != 0 && i % 9 != 8) {
                var fragment = fragments[i];

                if(fragment != null) {
                    setButton(i, fragment);
                }
            }
        }
    }

}
