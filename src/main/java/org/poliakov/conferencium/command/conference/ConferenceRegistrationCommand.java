package org.poliakov.conferencium.command.conference;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ParticipantServletCommand;
import org.poliakov.conferencium.dao.user.MysqlUserDaoImpl;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.user.UserService;
import org.poliakov.conferencium.service.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConferenceRegistrationCommand extends ParticipantServletCommand {
    private static final Logger LOGGER = Logger.getLogger(ConferenceRegistrationCommand.class);

    private final UserService userService;

    private final String conferencePageRedirect;

    public ConferenceRegistrationCommand() {
        LOGGER.info("Starting GetCreateConferencePageCommand");

        userService = new UserServiceImpl(MysqlUserDaoImpl.getInstance());

        conferencePageRedirect = PageMappingProperties.CONFERENCE_REDIRECT;
    }

    @Override
    public String restrictedExecute(HttpServletRequest request, HttpServletResponse response,
                                      String[] params) {
        Long conferenceId = Long.parseLong(params[0]);
        Long userId = (Long)request.getSession().getAttribute("id");

        userService.registerUserForConference(userId, conferenceId);
        return conferencePageRedirect + conferenceId;
    }
}
