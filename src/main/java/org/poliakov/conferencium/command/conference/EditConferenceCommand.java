package org.poliakov.conferencium.command.conference;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ModeratorServletCommand;
import org.poliakov.conferencium.dao.conference.MysqlConferenceDaoImpl;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.conference.ConferenceService;
import org.poliakov.conferencium.service.conference.ConferenceServiceImpl;
import org.poliakov.conferencium.util.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditConferenceCommand extends ModeratorServletCommand {
    private static final Logger LOGGER = Logger.getLogger(EditConferenceCommand.class);

    private final RequestParser requestParser;
    private final ConferenceService conferenceService;


    private final String page;
    private final String conferencePageRedirect;

    public EditConferenceCommand() {
        LOGGER.info("Starting GetCreateConferencePageCommand");

        requestParser = new RequestParser();
        conferenceService = new ConferenceServiceImpl(MysqlConferenceDaoImpl.getInstance());

        page = PageMappingProperties.EDIT_CONFERENCE_PAGE;
        conferencePageRedirect = PageMappingProperties.CONFERENCE_REDIRECT;
    }

    @Override
    protected String restrictedExecute(HttpServletRequest request, HttpServletResponse response,
                                       String[] params) {
        Conference conference = requestParser.parseConference(request);

        Long conferenceId = Long.parseLong(params[0]);
        conference.setId(conferenceId);

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

        conferenceService.updateConference(conference);
        return conferencePageRedirect + conferenceId;
    }
}
