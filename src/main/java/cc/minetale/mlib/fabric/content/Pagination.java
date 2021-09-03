package cc.minetale.mlib.fabric.content;

import cc.minetale.mlib.fabric.ClickableItem;
import cc.minetale.mlib.fabric.impl.IPagination;
import cc.minetale.mlib.fabric.impl.ISlotIterator;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Pagination implements IPagination {

    private int currentPage;
    private ClickableItem[] items = new ClickableItem[0];
    private int itemsPerPage = 5;

    @Override
    public ClickableItem[] getPageItems() {
        return Arrays.copyOfRange(items,
                currentPage * itemsPerPage,
                (currentPage + 1) * itemsPerPage);
    }

    @Override
    public int getPage() {
        return this.currentPage;
    }

    @Override
    public IPagination page(int page) {
        this.currentPage = page;
        return this;
    }

    @Override
    public boolean isFirst() {
        return this.currentPage <= 0;
    }

    @Override
    public boolean isLast() {
        return this.currentPage >= getPageCount() - 1;
    }

    @Override
    public IPagination first() {
        this.currentPage = 0;
        return this;
    }

    @Override
    public IPagination previous() {
        if (!isFirst())
            this.currentPage--;

        return this;
    }

    @Override
    public IPagination next() {
        if (!isLast())
            this.currentPage++;

        return this;
    }

    @Override
    public IPagination last() {
        this.currentPage = this.items.length / this.itemsPerPage;
        return this;
    }

    @Override
    public IPagination addToIterator(ISlotIterator iterator) {
        for (ClickableItem item : getPageItems()) {
            iterator.next().set(item);

            if (iterator.ended())
                break;
        }

        return this;
    }

    public IPagination setItems(ClickableItem... items) {
        this.items = items;
        return this;
    }

    public IPagination setItemsPerPage(int itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
        return this;
    }

    public int getPageCount() {
        return (int) Math.ceil((double) this.items.length / this.itemsPerPage);
    }

    @Override
    public int getNextPage() {
        int currentPage = this.currentPage + 1;

        if (!isLast())
            return currentPage + 1;

        return currentPage;
    }

    @Override
    public int getPreviousPage() {
        int currentPage = this.currentPage + 1;

        if (!isFirst())
            return currentPage - 1;

        return currentPage;
    }
}
