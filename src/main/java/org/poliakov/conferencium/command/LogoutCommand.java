package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.properties.PageMappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * User logout via GET requests
 */
public class LogoutCommand implements ServletCommand {
    private static final Logger LOGGER = Logger.getLogger(LogoutCommand.class);

    private static String mainPage;

    public LogoutCommand() {
        LOGGER.info("Starting LogoutCommand");

        mainPage = PageMappingProperties.CONFERENCES_PAGE;
    }

    public String execute(HttpServletRequest request, HttpServletResponse response, String[]... params) {
        LOGGER.info("Executing command");

        HttpSession session = request.getSession();
        session.invalidate();
        return mainPage;
    }
}
