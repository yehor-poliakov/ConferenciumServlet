package org.poliakov.conferencium.model.conference;

public class ConferenceSearchFilters {
    private boolean showPast;
    private boolean showFuture;
    private ConferenceSortType orderby;
    private Integer pageNumber;
    private Integer pageSize;

    public ConferenceSearchFilters() {
    }

    public ConferenceSearchFilters(boolean showPast, boolean showFuture, ConferenceSortType orderby, Integer pageNumber, Integer pageSize) {
        this.showPast = showPast;
        this.showFuture = showFuture;
        this.orderby = orderby;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public ConferenceSortType getOrderby() {
        return orderby;
    }

    public boolean isShowFuture() {
        return showFuture;
    }

    public boolean isShowPast() {
        return showPast;
    }
}