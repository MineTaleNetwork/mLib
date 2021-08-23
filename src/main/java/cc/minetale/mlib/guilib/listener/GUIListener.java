package cc.minetale.mlib.guilib.listener;

import cc.minetale.mlib.guilib.GUI;
import cc.minetale.mlib.guilib.GUIManager;
import cc.minetale.mlib.guilib.buttons.Button;
import cc.minetale.mlib.guilib.events.ButtonClickEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;

public class GUIListener {

    public static void preClickEvent(InventoryPreClickEvent e) {
        GUI gui = GUIManager.getGui((GUI) e.getInventory());

        if (gui == null)
            return;

        int slot = e.getSlot();

        Button btn = gui.getButton(slot);

        if (btn == null)
            return;

        e.setCancelled(gui.isReadOnly());

        if (btn.getEvent() != null) {
            btn.getEvent().clickEvent(new ButtonClickEvent(e));
        }
    }

}
