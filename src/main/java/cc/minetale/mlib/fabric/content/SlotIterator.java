package cc.minetale.mlib.fabric.content;

import cc.minetale.mlib.fabric.ClickableItem;
import cc.minetale.mlib.fabric.FabricInventory;
import cc.minetale.mlib.fabric.ISlotIterator;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter
public class SlotIterator implements ISlotIterator {

    private final FabricContents contents;
    private final FabricInventory inv;

    private int slot;

    public SlotIterator(FabricContents contents, FabricInventory inv, int slot) {
        this.contents = contents;
        this.inv = inv;
        this.slot = slot - 1;
    }

    @Override
    public Optional<ClickableItem> get() {
        return this.contents.getSlot(this.slot);
    }

    @Override
    public ISlotIterator set(ClickableItem item) {
        this.contents.setSlot(this.slot, item);
        return this;
    }

    @Override
    public ISlotIterator previous() {
        if(!this.started())
            this.slot--;

        return this;
    }

    @Override
    public ISlotIterator next() {
        if(!this.ended())
            this.slot++;

        return this;
    }

    @Override
    public int slot() {
        return this.getSlot();
    }

    @Override
    public ISlotIterator slot(int slot) {
        this.slot = slot;
        return this;
    }

    @Override
    public boolean started() {
        return this.slot == 0;
    }

    @Override
    public boolean ended() {
        return this.slot == inv.getType().getSize() - 1;
    }

}