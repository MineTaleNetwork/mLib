package cc.minetale.mlib.canvas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minestom.server.inventory.InventoryType;

@Getter @AllArgsConstructor
public enum CanvasType {

    ONE_ROW(InventoryType.CHEST_1_ROW),
    TWO_ROW(InventoryType.CHEST_2_ROW),
    THREE_ROW(InventoryType.CHEST_3_ROW),
    FOUR_ROW(InventoryType.CHEST_4_ROW),
    FIVE_ROW(InventoryType.CHEST_5_ROW),
    SIX_ROW(InventoryType.CHEST_6_ROW);

    private final InventoryType type;

    public int getSize() {
        return type.getSize();
    }

}
