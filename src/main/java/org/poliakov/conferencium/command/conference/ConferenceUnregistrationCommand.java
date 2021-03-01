package org.poliakov.conferencium.command.conference;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ParticipantServletCommand;
import org.poliakov.conferencium.command.ServletCommand;
import org.poliakov.conferencium.dao.user.MysqlUserDaoImpl;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.user.UserService;
import org.poliakov.conferencium.service.user.UserServiceImpl;
import org.poliakov.conferencium.util.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConferenceUnregistrationCommand extends ParticipantServletCommand {
    private static final Logger LOGGER = Logger.getLogger(ConferenceUnregistrationCommand.class);

    private final UserService userService;

    private final String conferencePageRedirect;

    public ConferenceUnregistrationCommand() {
        this(new UserServiceImpl(MysqlUserDaoImpl.getInstance()));
    }

    public ConferenceUnregistrationCommand(UserService userService) {
        LOGGER.info("Starting GetCreateConferencePageCommand");
        conferencePageRedirect = PageMappingProperties.CONFERENCE_REDIRECT;
        this.userService = userService;
    }

    @Override
    public String restrictedExecute(HttpServletRequest request, HttpServletResponse response,
                                    String[] params) {
        Long conferenceId = Long.parseLong(params[0]);
        Long userId = (Long) request.getSession().getAttribute("id");
        userService.unregisterUserFromConference(userId, conferenceId);
        return conferencePageRedirect + conferenceId;
    }
}
