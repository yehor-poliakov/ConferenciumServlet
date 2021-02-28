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

    public Conference parseConference(HttpServletRequest request) {
        ConferenceBuilder builder = new ConferenceBuilder();

        String title = request.getParameter("title");
        builder.setTitle(title);

        String location = request.getParameter("location");
        builder.setLocation(location);

        String dateStr = request.getParameter("date");
        if (dateStr != null) {
            builder.setDate(LocalDate.parse(dateStr));
        }

        return builder.build();
    }

    public Presentation parsePresentation(HttpServletRequest request) {
        PresentationBuilder builder = new PresentationBuilder();
        String topic = request.getParameter("topic");
        builder.setTopic(topic);

        String timeStr = request.getParameter("time");
        if (timeStr != null) {
            builder.setTime(LocalTime.parse(timeStr));
        }

        String conferenceIdStr = request.getParameter("conferenceid");
        builder.setConferenceId(Long.parseLong(conferenceIdStr));

        String presentationApprovedStr = request.getParameter("presentationApproved");
        builder.setPresentationApproved(Boolean.parseBoolean(presentationApprovedStr));

        String speakerApprovedStr = request.getParameter("presentationApproved");
        builder.setSpeakerApproved(Boolean.parseBoolean(speakerApprovedStr));

        builder.setSpeaker(request.getParameter("speaker"));

        String speakerIdStr = request.getParameter("speakerid");
        builder.setSpeakerId(Long.parseLong(speakerIdStr));

        return builder.build();
    }
}
