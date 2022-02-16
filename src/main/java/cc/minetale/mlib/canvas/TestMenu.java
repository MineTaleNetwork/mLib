package cc.minetale.mlib.canvas;

import cc.minetale.commonlib.cache.RequestCache;
import cc.minetale.mlib.canvas.template.PaginatedMenu;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.metadata.PlayerHeadMeta;

import java.util.concurrent.CompletableFuture;

public class TestMenu extends PaginatedMenu {

    public TestMenu(Player player) {
        super(player, Component.text("Friend Requests"), CanvasType.SIX_ROW, new Fragment[0]);

        CompletableFuture.runAsync(() -> {
            var outgoing = RequestCache.getFriendRequest().getOutgoing(player.getUuid()).stream().toList();
            var fragments = new Fragment[outgoing.size()];

            for (int i = 0; i < fragments.length; i++) {
                var request = outgoing.get(i);

                fragments[i] = Fragment.of(ItemStack.of(Material.PLAYER_HEAD)
                        .withMeta(PlayerHeadMeta.class, meta -> meta.skullOwner(request.target())), event -> {});
            }

            getPagination().setItems(fragments);
            setPaginatedItems();
        });
    }

}
