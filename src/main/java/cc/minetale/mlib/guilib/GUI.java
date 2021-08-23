package cc.minetale.mlib.guilib;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cc.minetale.mlib.guilib.buttons.Button;
import cc.minetale.mlib.guilib.buttons.StaticButton;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;
import net.minestom.server.item.ItemStack;

public class GUI extends Inventory {

    private Map<Integer, Button> buttons = new HashMap<>();
    private String id;
    private boolean readOnly = false;

    /**
     * Creates a basic GUI.
     *
     * @param type  the inventory type
     * @param title the inventory title
     * @param id    the gui ID
     */
    public GUI(InventoryType type, Component title, String id) {
        super(type, title);
        this.id = String.valueOf(title.hashCode());
    }

    /**
     * Add a Button to a GUI
     *
     * @param slot   slot for the button
     * @param button the actual button
     */
    public void addButton(int slot, Button button) {
        buttons.put(slot, button);
        this.setItemStack(slot, button.getItem());
    }

    /**
     * Add a Button to a GUI on multiple slots
     *
     * @param slots  the slots for the button
     * @param button the actual button
     */
    public void addButton(Collection<Integer> slots, Button button) {
        for (int slot : slots) {
            addButton(slot, button);
        }
    }

    /**
     * Get A Button from a certain slot,
     * null if no button exists.
     *
     * @param slot slot for the button
     * @return     the button at the slot
     */
    public Button getButton(int slot) {
        return buttons.get(slot);
    }

    /**
     * Sets whether this inventory is read-only.
     *
     * @param readOnly
     */
    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    /**
     * Get the ID of the GUI
     *
     * @return id of the gui
     */
    public String getId() {
        return id;
    }

    /**
     * Open the GUI for a player
     *
     * @param p player to open the gui
     */
    public void open(Player p) {
        p.openInventory(this);
    }

    /**
     * Gets whether this inventory is read-only
     *
     * @return readOnly
     */
    public boolean isReadOnly() {
        return readOnly;
    }

}
