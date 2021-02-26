package org.poliakov.conferencium.model.conference;

public class ConferenceSearchFiltersBuilder {
    private boolean showPast;
    private boolean showFuture;
    private ConferenceSortType orderby;
    private Integer pageNumber;
    private Integer pageSize;

    public ConferenceSearchFiltersBuilder() {
        orderby = ConferenceSortType.DateDesc;
        pageNumber = 1;
        pageSize = 9;
    }

    public ConferenceSearchFiltersBuilder setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public ConferenceSearchFiltersBuilder setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public ConferenceSearchFiltersBuilder setOrderby(ConferenceSortType orderby) {
        this.orderby = orderby;
        return this;
    }

    public ConferenceSearchFiltersBuilder setShowFuture(boolean showFuture) {
        this.showFuture = showFuture;
        return this;
    }

    public ConferenceSearchFiltersBuilder setShowPast(boolean showPast) {
        this.showPast = showPast;
        return this;
    }

    public ConferenceSearchFilters build() {
        if (!showPast && !showFuture) {
            showPast = true;
            showFuture = true;
        }

        return new ConferenceSearchFilters(showPast, showFuture, orderby, pageNumber, pageSize);
    }
}
