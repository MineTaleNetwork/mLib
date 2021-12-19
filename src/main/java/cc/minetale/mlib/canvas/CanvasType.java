package cc.minetale.mlib.canvas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.inventory.InventoryType;

@Getter @AllArgsConstructor
public enum CanvasType {

    ONE_ROW(9, InventoryType.CHEST_1_ROW),
    TWO_ROW(18, InventoryType.CHEST_2_ROW),
    THREE_ROW(27, InventoryType.CHEST_3_ROW),
    FOUR_ROW(36, InventoryType.CHEST_4_ROW),
    FIVE_ROW(45, InventoryType.CHEST_5_ROW),
    SIX_ROW(54, InventoryType.CHEST_6_ROW);

    private int size;
    private InventoryType type;

}
