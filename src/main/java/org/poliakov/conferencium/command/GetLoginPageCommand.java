package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.properties.PageMappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetLoginPageCommand implements ServletCommand{
    private static final Logger LOGGER = Logger.getLogger(GetLoginPageCommand.class);

    private static String loginPage;
    private static String mainPage;

    public GetLoginPageCommand(){
        LOGGER.info("Starting GetLoginPageCommand");

        loginPage = PageMappingProperties.LOGIN_PAGE;
        mainPage = PageMappingProperties.MAIN_PAGE;
    }

    public String execute(HttpServletRequest request, HttpServletResponse response, String[] ...params) {
        LOGGER.info("Executing command");

        String resultPage = loginPage;

        if(request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("authenticated").equals(true)) {
            resultPage = mainPage;
        }
        else if(request.getParameter("email") == null && request.getParameter("password") == null) {
            LOGGER.info("Returning login page");
            return resultPage;
        }

        return resultPage;
    }
}
