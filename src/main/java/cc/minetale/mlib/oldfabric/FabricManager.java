package cc.minetale.mlib.oldfabric;

import cc.minetale.mlib.fabric.inventory.FabricContents;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.EventFilter;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.inventory.InventoryCloseEvent;
import net.minestom.server.event.inventory.InventoryOpenEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.trait.InventoryEvent;
import net.minestom.server.event.trait.PlayerEvent;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.utils.time.Tick;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FabricManager {

    private final Map<UUID, FabricInventory> inventories;
    private final Map<UUID, FabricContents> contents;

    public FabricManager() {
        this.inventories = new ConcurrentHashMap<>();
        this.contents = new ConcurrentHashMap<>();
    }

    public void initialize() {
        MinecraftServer.getGlobalEventHandler().addChild(playerEvents());
        MinecraftServer.getGlobalEventHandler().addChild(inventoryEvents());

        MinecraftServer.getSchedulerManager().buildTask(() -> {
            new HashMap<>(this.inventories).forEach((uuid, fabric) -> {
                Player player = MinecraftServer.getConnectionManager().getPlayer(uuid);

                try {
                    fabric.getProvider().update(player, this.contents.get(uuid));
                } catch (Exception e) {
                    handleInventoryUpdateError(fabric, player, e);
                }
            });
        }).repeat(Tick.server(1)).schedule();
    }

    public List<Player> getOpenedPlayers(FabricInventory inv) {
        List<Player> list = new ArrayList<>();

        this.inventories.forEach((player, playerInv) -> {
            if (inv.equals(playerInv))
                list.add(MinecraftServer.getConnectionManager().getPlayer(player));
        });

        return list;
    }

    public Optional<FabricContents> getContents(Player player) {
        return Optional.ofNullable(this.contents.get(player.getUuid()));
    }

    public Optional<FabricInventory> getInventory(UUID uuid) {
        return Optional.ofNullable(this.inventories.get(uuid));
    }

    protected void setInventory(Player player, FabricInventory inv) {
        if (inv == null)
            this.inventories.remove(player.getUuid());
        else
            this.inventories.put(player.getUuid(), inv);
    }

    protected void setContents(Player player, FabricContents contents) {
        if (contents == null)
            this.contents.remove(player.getUuid());
        else
            this.contents.put(player.getUuid(), contents);
    }

    public void handleInventoryUpdateError(FabricInventory fabric, Player player, Exception exception) {
        fabric.close(player);

        MinecraftServer.LOGGER.error("Error while updating FabricInventory:", exception);
    }

    public void handleInventoryOpenError(FabricInventory fabric, Player player, Exception exception) {
        fabric.close(player);

        MinecraftServer.LOGGER.error("Error while opening FabricInventory:", exception);
    }

    private EventNode<PlayerEvent> playerEvents() {
        return EventNode.type("player-events", EventFilter.PLAYER)
                .addListener(PlayerDisconnectEvent.class, event -> {
                    Player player = event.getPlayer();

                    this.inventories.remove(player.getUuid());
                    this.contents.remove(player.getUuid());
                });
    }

    private EventNode<InventoryEvent> inventoryEvents() {
        return EventNode.type("inventory-events", EventFilter.INVENTORY)
                .addListener(InventoryPreClickEvent.class, event -> {
                    Player player = event.getPlayer();

                    if (!inventories.containsKey(player.getUuid()))
                        return;

                    FabricInventory fabric = this.inventories.get(player.getUuid());

                    if(fabric.isReadOnly())
                        event.setCancelled(true);

                    this.contents.get(player.getUuid()).getSlot(event.getSlot()).ifPresent(item -> item.run(event));

                    Inventory inventory = player.getOpenInventory();

                    if(inventory != null)
                        inventory.update(player);
                })
                .addListener(InventoryOpenEvent.class, event -> {
                    Player player = event.getPlayer();

                    if (!this.inventories.containsKey(player.getUuid()))
                        return;

                    FabricInventory fabric = this.inventories.get(player.getUuid());

                    fabric.getProvider().open(event, getContents(player).get());
                })
                .addListener(InventoryCloseEvent.class, event -> {
                    Player player = event.getPlayer();

                    if (!this.inventories.containsKey(player.getUuid()))
                        return;

                    FabricInventory fabric = this.inventories.get(player.getUuid());

                    fabric.getProvider().close(event);

                    Inventory inventory = event.getInventory();

                    if(inventory != null)
                        inventory.clear();

                    this.inventories.remove(player.getUuid());
                    this.contents.remove(player.getUuid());
                });
    }


}
