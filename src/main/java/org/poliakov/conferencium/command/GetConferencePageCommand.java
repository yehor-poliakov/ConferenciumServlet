package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.dao.conference.MysqlConferenceDaoImpl;
import org.poliakov.conferencium.dao.presentation.MysqlPresentationDaoImpl;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.model.conference.ConferenceSearchFilters;
import org.poliakov.conferencium.model.conference.ConferenceSortType;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.conference.ConferenceService;
import org.poliakov.conferencium.service.conference.ConferenceServiceImpl;
import org.poliakov.conferencium.service.presentation.PresentationService;
import org.poliakov.conferencium.service.presentation.PresentationServiceImpl;
import org.poliakov.conferencium.util.Page;
import org.poliakov.conferencium.util.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class GetConferencePageCommand implements ServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetConferencePageCommand.class);

    private static ConferenceService conferenceService;
    private static PresentationService presentationService;

    private static String page;

    public GetConferencePageCommand() {
        LOGGER.info("Starting GetConferencePageCommand");

        conferenceService = new ConferenceServiceImpl(MysqlConferenceDaoImpl.getInstance());
        presentationService = new PresentationServiceImpl(MysqlPresentationDaoImpl.getInstance());

        page = PageMappingProperties.CONFERENCE_VIEW_PAGE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String[]... params) {
        String id = (String) Arrays.stream(request.getContextPath().split("/")).toArray()[1];
        Long conferenceId = Long.parseLong(id);

        Conference conference = conferenceService.getConferenceById(conferenceId);
        List<Presentation> presentations = presentationService.findAllByConferenceId(conferenceId);

        request.setAttribute("conference", conference);
        request.setAttribute("presentations", presentations);
        return page;
    }
}
