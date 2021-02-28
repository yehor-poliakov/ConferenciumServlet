package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.dao.user.MysqlUserDaoImpl;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.user.UserService;
import org.poliakov.conferencium.service.user.UserServiceImpl;
import org.poliakov.conferencium.util.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConferenceUnregistrationCommand implements ServletCommand {
    private static final Logger LOGGER = Logger.getLogger(ConferenceUnregistrationCommand.class);

    private final RequestParser requestParser;
    private final UserService userService;

    private final String conferencesPageRedirect;

    public ConferenceUnregistrationCommand() {
        LOGGER.info("Starting GetCreateConferencePageCommand");

        requestParser = new RequestParser();
        userService = new UserServiceImpl(MysqlUserDaoImpl.getInstance());

        conferencesPageRedirect = PageMappingProperties.MAIN_PAGE_REDIRECT;
    }

    @Override
    public String execute (HttpServletRequest request, HttpServletResponse response,
                                      String[] params) {
        Long conferenceId = Long.parseLong(params[0]);
        String userName = request.getUserPrincipal().getName();
        Long userId = userService.findUserByEmail(userName).getId();
        userService.unregisterUserFromConference(userName, conferenceId);
        return conferencesPageRedirect;
    }
}