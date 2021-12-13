package cc.minetale.mlib.fabric.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.inventory.InventoryType;

@Getter @AllArgsConstructor
public enum FabricType {
    ONE_ROW(9, 1, InventoryType.CHEST_1_ROW),
    TWO_ROW(9, 2, InventoryType.CHEST_2_ROW),
    THREE_ROW(9, 3, InventoryType.CHEST_3_ROW),
    FOUR_ROW(9, 4, InventoryType.CHEST_4_ROW),
    FIVE_ROW(9, 5, InventoryType.CHEST_5_ROW),
    SIX_ROW(9, 6, InventoryType.CHEST_6_ROW);

    private int width;
    private int height;
    private InventoryType type;



}
