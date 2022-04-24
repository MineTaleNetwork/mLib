package cc.minetale.mlib.canvas.template;

import cc.minetale.mlib.canvas.CanvasType;
import cc.minetale.mlib.canvas.Filler;
import cc.minetale.mlib.canvas.Fragment;
import cc.minetale.mlib.util.SoundsUtil;
import cc.minetale.sodium.util.Message;
import cc.minetale.sodium.util.Pagination;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

@Getter @Setter
public abstract class PaginatedMenu extends Menu {

    private Pagination<Fragment> pagination;
    private Menu previousPage;

    public PaginatedMenu(Player player, Component title, CanvasType type) {
        super(player, title, type);
    }

    @Override
    public void initialize() {
        var type = getType();
        var player = getPlayer();

        if(type.getSize() < 27) {
            type = CanvasType.THREE_ROW;
        }

        var size = type.getSize();

        pagination = new Pagination<>((size / 9 - 2) * 7, getPaginatedFragments(player));

        setFiller(Filler.BORDER);

        setButton(size - 6, Fragment.of(ItemStack.of(Material.GRAY_CARPET)
                .withDisplayName(Component.text("\u2190 Previous Page", Message.style(NamedTextColor.GRAY))), event -> {
            if(pagination.previousPage()) {
                SoundsUtil.playClickSound(player);
                setPaginatedItems();
            } else {
                SoundsUtil.playErrorSound(player);
            }
        }));

        setButton(size - 5, Fragment.of(ItemStack.of(Material.ARROW)
                .withDisplayName(Component.text("\u2193 " + (previousPage != null ? "Back" : "Exit") + " \u2193", Message.style(NamedTextColor.GRAY))), event -> {
            handleClose(player);

            if(previousPage != null) {
                openMenu(previousPage);
            }

            SoundsUtil.playClickSound(player);
        }));

        setButton(size - 4, Fragment.of(ItemStack.of(Material.GRAY_CARPET)
                        .withDisplayName(Component.text("Next Page \u2192", Message.style(NamedTextColor.GRAY))),
                event -> {
                    if(pagination.nextPage()) {
                        SoundsUtil.playClickSound(player);
                        setPaginatedItems();
                    } else {
                        SoundsUtil.playErrorSound(player);
                    }
                }));

        setPaginatedItems();
    }

    public void setPaginatedItems() {
        var fragments = pagination.getPageItems();

        var size = getType().getSize();

        for (int index = 0, fragmentIndex = 0; index < size; index++) {
            if (index > 9 && index <= size - 9 && index % 9 != 0 && index % 9 != 8) {
                if(fragmentIndex < fragments.length && fragments[fragmentIndex] != null) {
                    setButton(index, fragments[fragmentIndex]);
                    fragmentIndex++;
                }
            }
        }
    }

    public abstract Fragment[] getPaginatedFragments(Player player);

}
