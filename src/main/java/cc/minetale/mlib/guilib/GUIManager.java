package cc.minetale.mlib.guilib;

import net.kyori.adventure.text.Component;
import net.minestom.server.inventory.Inventory;

import java.util.*;

public class GUIManager {

    private static Map<String, GUI> idMap = new HashMap<>();
    private static Map<Component, GUI> titleMap = new HashMap<>();
    private static List<GUI> inventoryList = new ArrayList<>();

    /**
     * Register a GUI.
     * You can get it later by doing {@link #getGuiById(String)} and refering to the GUIs ID
     *
     * @param gui the gui
     */
    public static void registerGUI(GUI gui) {
        titleMap.put(gui.getTitle(), gui);
        idMap.put(gui.getId(), gui);
        inventoryList.add(gui);
    }

    /**
     * Get all registered GUIs
     *
     * @return all registered GUIs
     */
    public static Collection<GUI> getGuis() {
        return idMap.values();
    }

    /**
     * Get a GUI by the inventory title
     * returns null if no GUI was found.
     *
     * @param title the title of the inventory
     * @return the gui found
     */
    public static GUI getGui(Component title) {
        return titleMap.getOrDefault(title, null);
    }

    /**
     * Get a GUI by the GUI id
     * returns null if no GUI was found.
     *
     * @param id the id of the gui
     * @return the gui found
     */
    public static GUI getGui(String id) {
        return idMap.getOrDefault(id, null);
    }

    /**
     * Get a GUI by the Inventory
     * returns null if no GUI was found.
     *
     * @param gui the gui
     * @return the gui found
     */
    public static GUI getGui(GUI gui) {
        return inventoryList.stream().filter(inventory -> inventory.getId().equals(gui.getId())).findFirst().orElse(null);
    }

}
