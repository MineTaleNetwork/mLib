package cc.minetale.mlib.fabric.impl;

import cc.minetale.mlib.fabric.ClickableItem;

import java.util.Optional;

public interface ISlotIterator {
    Optional<ClickableItem> get();
    ISlotIterator set(ClickableItem item);

    ISlotIterator previous();
    ISlotIterator next();

    int slot();
    ISlotIterator slot(int slot);

    boolean started();
    boolean ended();
}