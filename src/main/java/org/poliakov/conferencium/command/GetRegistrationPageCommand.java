package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.properties.PageMappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetRegistrationPageCommand implements ServletCommand{
    private static final Logger LOGGER = Logger.getLogger(GetRegistrationPageCommand.class);

    private static String registrationPage;
    private static String mainPage;

    public GetRegistrationPageCommand(){
        LOGGER.info("Starting GetLoginPageCommand");

        registrationPage = PageMappingProperties.REGISTRATION_PAGE;
        mainPage = PageMappingProperties.MAIN_PAGE;
    }

    public String execute(HttpServletRequest request, HttpServletResponse response, String[] ...params) {
        LOGGER.info("Executing command");

        String resultPage = registrationPage;

        if(request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("authenticated").equals(true)) {
            resultPage = mainPage;
        }
        else if(request.getParameter("email") == null && request.getParameter("password") == null
        && request.getParameter("firstname") == null && request.getParameter("lastname") == null) {
            LOGGER.info("Returning registration page");
            return resultPage;
        }

        return resultPage;
    }
}
