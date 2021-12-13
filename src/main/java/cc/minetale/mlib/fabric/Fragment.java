package cc.minetale.mlib.fabric;

import cc.minetale.mlib.fabric.listener.FragmentClickListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.ItemStack;

import java.util.ArrayList;

@Getter @AllArgsConstructor
public class Fragment {

    private final int x, y, width, height;
    private final ItemStack itemStack;
    @Setter private boolean stealable;
    @Setter private FragmentClickListener clickListener;

    public Inventory render(Inventory inventory) {
        int basePosition = this.x;
        for (int i = 0; i < this.y; i++) basePosition += 9;

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                inventory.setItemStack(basePosition + j + (i * 9), this.itemStack);
            }
        }

        return inventory;
    }

    public ArrayList<Integer> getOccupiedSlots() {
        ArrayList<Integer> occupiedSlots = new ArrayList<>();
        int basePosition = this.x;
        for (int i = 0; i < this.y; i++) basePosition += 9;

        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                occupiedSlots.add(basePosition + j + (i * 9));
            }
        }

        return occupiedSlots;
    }

}
