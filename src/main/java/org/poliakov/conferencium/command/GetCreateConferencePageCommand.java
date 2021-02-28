package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.properties.PageMappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetCreateConferencePageCommand extends ModeratorServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetCreateConferencePageCommand.class);

    private final String page;

    public GetCreateConferencePageCommand() {
        LOGGER.info("Starting GetCreateConferencePageCommand");

        page = PageMappingProperties.CREATE_CONFERENCE_PAGE;
    }

    @Override
    protected String moderatorExecute(HttpServletRequest request, HttpServletResponse response,
                                      String[] params) {
        Conference conference = new Conference();
        request.setAttribute("conference", conference);
        return page;
    }
}
