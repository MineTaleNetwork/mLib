package cc.minetale.mlib.canvas;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter @Setter
public class Pagination {

    private Fragment[] fragments = new Fragment[0];
    private int currentPage = 0;

    private int start, itemsPerPage;
    private boolean titleUpdated;

    public Pagination(int start, int itemsPerPage, boolean titleUpdated) {
        this.start = start;
        this.itemsPerPage = itemsPerPage;
        this.titleUpdated = titleUpdated;
    }

    public Fragment[] getPageItems() {
        return Arrays.copyOfRange(this.fragments,
                this.currentPage * this.itemsPerPage,
                (this.currentPage + 1) * this.itemsPerPage);
    }

    public int getPageCount() {
        return (int) Math.ceil((double) this.fragments.length / this.itemsPerPage);
    }

    public boolean isFirst() {
        return this.currentPage == 0;
    }

    public boolean isLast() {
        return this.currentPage == getPageCount() - 1;
    }

    public boolean nextPage() {
        if(isLast()) {
            return false;
        } else {
            this.currentPage++;
            return true;
        }
    }

    public boolean previousPage() {
        if(isFirst()) {
            return false;
        } else {
            this.currentPage--;
            return true;
        }
    }

}
