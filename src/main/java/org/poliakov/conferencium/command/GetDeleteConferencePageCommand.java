package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.dao.conference.MysqlConferenceDaoImpl;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.conference.ConferenceService;
import org.poliakov.conferencium.service.conference.ConferenceServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetDeleteConferencePageCommand extends ModeratorServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetDeleteConferencePageCommand.class);

    public GetDeleteConferencePageCommand() {
        LOGGER.info("Starting GetEditConferencePageCommand");

    }

    @Override
    protected String moderatorExecute(HttpServletRequest request, HttpServletResponse response,
                                      String[] params) {
        Long conferenceId = Long.parseLong(params[0]);
        return "conference/" + conferenceId + "/delete";
    }
}
