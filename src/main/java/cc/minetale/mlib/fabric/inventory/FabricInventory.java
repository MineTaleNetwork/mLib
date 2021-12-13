package cc.minetale.mlib.fabric.inventory;

import cc.minetale.mlib.fabric.event.FragmentClickEvent;
import cc.minetale.mlib.fabric.Fragment;
import cc.minetale.mlib.fabric.listener.FragmentClickListener;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.player.PlayerTickEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;

import java.util.ArrayList;

@Getter
public abstract class FabricInventory implements FabricProvider {

    private final Component title;
    private final FabricType type;
    private ArrayList<Fragment> fragments;

    public FabricInventory(Component title, FabricType type) {
        this.title = title;
        this.type = type;
    }

    private Inventory render() {
        return render(new Inventory(this.type.getType(), this.title));
    }

    private Inventory render(Inventory inventory) {
        for (Fragment fragment : this.fragments) {
            inventory = fragment.render(inventory);
        }
        return inventory;
    }

    public void fill(ItemStack itemStack) {
        addFragment(0, 0, this.type.getWidth(), this.type.getHeight(), itemStack, false, (e) -> {});
    }

    public void addFragment(int x, int y, int width, int height, ItemStack itemStack, boolean stealable, FragmentClickListener listener) {
        this.fragments.add(new Fragment(x, y, width, height, itemStack, stealable, listener));
    }

    public void open(Player player) {
        var inventory = render();

        EventNode<Event> eventNode = EventNode.all("fabric");

        eventNode.addListener(EventListener.builder(InventoryPreClickEvent.class)
                .expireWhen(ignored -> {
                    var openInventory = player.getOpenInventory();

                    return openInventory == null || !openInventory.getTitle().equals(this.title);
                })
                .handler(e -> {
                    int slot = e.getSlot();

                    for (Fragment fragment : this.fragments) {
                        if (fragment.getOccupiedSlots().contains(slot)) {
                            if (!fragment.isStealable()) e.setCancelled(true);

                            fragment.getClickListener().onClick(new FragmentClickEvent(e, this));
                        }
                    }
                }).build());

        MinecraftServer.getGlobalEventHandler().addChild(eventNode);

        player.openInventory(inventory);
    }

}
