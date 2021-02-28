package org.poliakov.conferencium.command.conference;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ModeratorServletCommand;
import org.poliakov.conferencium.dao.conference.MysqlConferenceDaoImpl;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.conference.ConferenceService;
import org.poliakov.conferencium.service.conference.ConferenceServiceImpl;
import org.poliakov.conferencium.util.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteConferenceCommand extends ModeratorServletCommand {
    private static final Logger LOGGER = Logger.getLogger(DeleteConferenceCommand.class);

    private final ConferenceService conferenceService;

    private final String conferencesPageRedirect;

    public DeleteConferenceCommand() {
        LOGGER.info("Starting GetCreateConferencePageCommand");

        conferenceService = new ConferenceServiceImpl(MysqlConferenceDaoImpl.getInstance());

        conferencesPageRedirect = PageMappingProperties.MAIN_PAGE_REDIRECT;
    }

    @Override
    protected String restrictedExecute(HttpServletRequest request, HttpServletResponse response,
                                       String[] params) {
        Long conferenceId = Long.parseLong(params[0]);
        conferenceService.deleteConference(conferenceId);
        return conferencesPageRedirect;
    }
}
