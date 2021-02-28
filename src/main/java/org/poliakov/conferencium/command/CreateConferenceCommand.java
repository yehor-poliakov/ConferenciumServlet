package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.dao.conference.MysqlConferenceDaoImpl;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.conference.ConferenceService;
import org.poliakov.conferencium.service.conference.ConferenceServiceImpl;
import org.poliakov.conferencium.util.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateConferenceCommand extends ModeratorServletCommand {
    private static final Logger LOGGER = Logger.getLogger(CreateConferenceCommand.class);

    private final RequestParser requestParser;
    private final ConferenceService conferenceService;

    private final String page;
    private final String conferencesPageRedirect;

    public CreateConferenceCommand() {
        LOGGER.info("Starting CreateConferenceCommand");

        requestParser = new RequestParser();
        conferenceService = new ConferenceServiceImpl(MysqlConferenceDaoImpl.getInstance());

        page = PageMappingProperties.CREATE_CONFERENCE_PAGE;
        conferencesPageRedirect = PageMappingProperties.MAIN_PAGE_REDIRECT;

    }

    @Override
    protected String moderatorExecute(HttpServletRequest request, HttpServletResponse response,
                                      String[] params) {
        System.out.println("createcommand");
        Conference conference = requestParser.parseConference(request);

        boolean error = false;

        if (conference.getTitle() == null || conference.getTitle().equals("")) {
            request.setAttribute("titleError", true);
            error = true;
        }

        if (conference.getLocation() == null || conference.getLocation().equals("")) {
            request.setAttribute("locationError", true);
            error = true;
        }

        if (conference.getDate() == null) {
            request.setAttribute("dateError", true);
            error = true;
        }

        if (error) {
            request.setAttribute("conference", conference);
            return page;
        }

        conferenceService.createConference(conference);
        return conferencesPageRedirect;
    }
}
