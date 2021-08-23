package cc.minetale.mlib.guilib;

import cc.minetale.mlib.guilib.listener.GUIListener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.inventory.InventoryPreClickEvent;

public class GuiLib {

    private static boolean initialized = false;

    public static void init() {
        if (initialized) {
            throw new IllegalStateException("GuiLib already initialized!");
        } else {
            initialized = true;

            MinecraftServer.getGlobalEventHandler().addListener(InventoryPreClickEvent.class, GUIListener::preClickEvent);
        }
    }

    public static boolean isInitialized() {
        return initialized;
    }

}
