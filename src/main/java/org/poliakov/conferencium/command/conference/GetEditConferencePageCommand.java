package org.poliakov.conferencium.command.conference;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ModeratorServletCommand;
import org.poliakov.conferencium.dao.conference.MysqlConferenceDaoImpl;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.conference.ConferenceService;
import org.poliakov.conferencium.service.conference.ConferenceServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetEditConferencePageCommand extends ModeratorServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetEditConferencePageCommand.class);

    private final ConferenceService conferenceService;
    private final String page;

    public GetEditConferencePageCommand() {
        LOGGER.info("Starting GetEditConferencePageCommand");
        conferenceService = new ConferenceServiceImpl(MysqlConferenceDaoImpl.getInstance());
        page = PageMappingProperties.EDIT_CONFERENCE_PAGE;
    }

    @Override
    protected String restrictedExecute(HttpServletRequest request, HttpServletResponse response,
                                       String[] params) {
        Long conferenceId = Long.parseLong(params[0]);
        Conference conference = conferenceService.getConferenceById(conferenceId);
        request.setAttribute("conference", conference);
        return page;
    }
}
