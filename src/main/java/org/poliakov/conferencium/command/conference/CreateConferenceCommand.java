package org.poliakov.conferencium.command.conference;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ModeratorServletCommand;
import org.poliakov.conferencium.dao.conference.MysqlConferenceDaoImpl;
import org.poliakov.conferencium.dao.user.MysqlUserDaoImpl;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.conference.ConferenceService;
import org.poliakov.conferencium.service.conference.ConferenceServiceImpl;
import org.poliakov.conferencium.service.user.UserService;
import org.poliakov.conferencium.service.user.UserServiceImpl;
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
        this(new ConferenceServiceImpl(MysqlConferenceDaoImpl.getInstance()),
                new RequestParser());
    }

    public CreateConferenceCommand(ConferenceService conferenceService, RequestParser requestParser) {
        LOGGER.info("Starting CreateConferenceCommand");

        this.requestParser = requestParser;
        this.conferenceService = conferenceService;

        page = PageMappingProperties.CREATE_CONFERENCE_PAGE;
        conferencesPageRedirect = PageMappingProperties.MAIN_PAGE_REDIRECT;
    }

    @Override
    public String restrictedExecute(HttpServletRequest request, HttpServletResponse response,
                                       String[] params) {
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
