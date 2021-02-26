package org.poliakov.conferencium.util;

import java.util.List;

public class Page<T> {

    private List<T> items;
    private Integer number;
    private Integer size;
    private Integer currentSize;
    private boolean first;
    private boolean last;
    private Integer total;
    private Integer totalPages;


    public Page(List<T> items, Integer number, Integer size, Integer total) {
        this.items = items;
        this.number = number;
        this.size = size;
        this.total = total;

        currentSize = items.size();
        first = number == 1;
        last = currentSize < size;
        totalPages = (int)Math.ceil((double)total / size);
    }

    public List<T> getItems() {
        return items;
    }

    public Integer getNumber() {
        return number;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getCurrentSize() {
        return currentSize;
    }

    public boolean isFirst() {
        return first;
    }

    public boolean isLast() {
        return last;
    }

    public Integer getTotalPages() {
        return totalPages;
    }
}
