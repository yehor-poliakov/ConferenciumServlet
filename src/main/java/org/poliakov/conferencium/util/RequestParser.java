package org.poliakov.conferencium.util;

import org.poliakov.conferencium.model.conference.ConferenceSearchFilters;
import org.poliakov.conferencium.model.conference.ConferenceSearchFiltersBuilder;
import org.poliakov.conferencium.model.conference.ConferenceSortType;

import javax.servlet.http.HttpServletRequest;

public class RequestParser {
    public ConferenceSearchFilters parseFilters(HttpServletRequest request) {
        ConferenceSearchFiltersBuilder builder = new ConferenceSearchFiltersBuilder();

        String showPastStr = request.getParameter("showPast");
        if (showPastStr != null) {
            Boolean showPast = showPastStr.equals("on");
            builder.setShowPast(showPast);
        } else {
            builder.setShowPast(false);
        }

        String showFutureStr = request.getParameter("showFuture");
        if (showFutureStr != null) {
            Boolean showFuture = showFutureStr.equals("on");
            builder.setShowFuture(showFuture);
        } else {
            builder.setShowFuture(false);
        }

        String orderbyStr = request.getParameter("orderby");
        if (orderbyStr != null) {
            ConferenceSortType orderby = ConferenceSortType.valueOf(orderbyStr);
            builder.setOrderby(orderby);
        }

        String pageNumberStr = request.getParameter("pageNumber");
        if (pageNumberStr != null) {
            Integer pageNumber = Integer.parseInt(pageNumberStr);
            builder.setPageNumber(pageNumber);
        }

        String pageSizeStr = request.getParameter("pageSize");
        if (pageSizeStr != null) {
            Integer pageSize = Integer.parseInt(pageSizeStr);
            builder.setPageSize(pageSize);
        }

        return builder.build();
    }
}
