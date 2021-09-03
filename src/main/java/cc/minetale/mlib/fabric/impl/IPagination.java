package cc.minetale.mlib.fabric.impl;

import cc.minetale.mlib.fabric.ClickableItem;

public interface IPagination {

    ClickableItem[] getPageItems();

    int getPageCount();
    int getPage();
    IPagination page(int page);

    int getNextPage();
    int getPreviousPage();

    boolean isFirst();
    boolean isLast();

    IPagination first();
    IPagination previous();
    IPagination next();
    IPagination last();

    IPagination addToIterator(ISlotIterator iterator);
}