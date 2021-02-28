package org.poliakov.conferencium.util;

import org.poliakov.conferencium.model.conference.*;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.model.presentation.PresentationBuilder;
import org.poliakov.conferencium.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;

public class RequestParser {
    public ConferenceSearchFilters parseFilters(HttpServletRequest request) {
        ConferenceSearchFiltersBuilder builder = new ConferenceSearchFiltersBuilder();

        boolean showPast = parseBoolean(request, "showPast");
        builder.setShowPast(showPast);

        boolean showFuture = parseBoolean(request, "showFuture");
        builder.setShowFuture(showFuture);

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

    public Conference parseConference(HttpServletRequest request) {
        ConferenceBuilder builder = new ConferenceBuilder();

        String title = request.getParameter("title");
        builder.setTitle(title);

        String location = request.getParameter("location");
        builder.setLocation(location);

        String dateStr = request.getParameter("date");
        if (dateStr != null && !dateStr.equals("")) {
            builder.setDate(LocalDate.parse(dateStr));
        }

        return builder.build();
    }

    public Presentation parsePresentation(HttpServletRequest request) {
        PresentationBuilder builder = new PresentationBuilder();

        String topic = request.getParameter("topic");
        builder.setTopic(topic);

        String timeStr = request.getParameter("time");
        if (timeStr != null && !timeStr.equals("")) {
            builder.setTime(LocalTime.parse(timeStr));
        }

        boolean presentationApproved = parseBoolean(request, "presentationApproved");
        builder.setPresentationApproved(presentationApproved);

        boolean speakerApproved = parseBoolean(request, "speakerApproved");
        builder.setSpeakerApproved(speakerApproved);

        Long speakerId = parseLong(request, "speakerId");
        if (speakerId != null) {
            builder.setSpeakerId(speakerId);
        }

        Long conferenceId = parseLong(request, "conferenceId");
        if (conferenceId != null) {
            builder.setConferenceId(conferenceId);
        }

        return builder.build();
    }

    private boolean parseBoolean(HttpServletRequest request, String name) {
        String str = request.getParameter(name);
        return str != null && str.equals("on");
    }

    private Long parseLong(HttpServletRequest request, String name) {
        String str = request.getParameter(name);
        if (str == null || str.equals("")) {
            return null;
        }
        return Long.parseLong(str);
    }
}
