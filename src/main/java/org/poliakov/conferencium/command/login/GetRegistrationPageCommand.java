package org.poliakov.conferencium.command.login;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ServletCommand;
import org.poliakov.conferencium.properties.PageMappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRegistrationPageCommand implements ServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetRegistrationPageCommand.class);

    private final String registrationPage;
    private final String mainPageRedirect;

    public GetRegistrationPageCommand() {
        LOGGER.info("Starting GetLoginPageCommand");

        registrationPage = PageMappingProperties.REGISTRATION_PAGE;
        mainPageRedirect = PageMappingProperties.MAIN_PAGE_REDIRECT;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String[] params) {
        LOGGER.info("Executing command");

        String resultPage = registrationPage;

        if (request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("authenticated").equals(true)) {
            resultPage = mainPageRedirect;
        }

        return resultPage;
    }
}
