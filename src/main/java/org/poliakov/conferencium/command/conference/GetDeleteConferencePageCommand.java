package org.poliakov.conferencium.command.conference;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ModeratorServletCommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetDeleteConferencePageCommand extends ModeratorServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetDeleteConferencePageCommand.class);

    public GetDeleteConferencePageCommand() {
        LOGGER.info("Starting GetEditConferencePageCommand");

    }

    @Override
    protected String restrictedExecute(HttpServletRequest request, HttpServletResponse response,
                                       String[] params) {
        Long conferenceId = Long.parseLong(params[0]);
        return "conference/" + conferenceId + "/delete";
    }
}
