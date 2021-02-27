package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.properties.PageMappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetLoginPageCommand implements ServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetLoginPageCommand.class);

    private final String loginPage;
    private final String mainPageRedirect;

    public GetLoginPageCommand(){
        LOGGER.info("Starting GetLoginPageCommand");

        loginPage = PageMappingProperties.LOGIN_PAGE;
        mainPageRedirect = PageMappingProperties.MAIN_PAGE_REDIRECT;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String[] params) {
        LOGGER.info("Executing command");

        String resultPage = loginPage;

        if(request.getSession().getAttribute("authenticated") != null &&
                request.getSession().getAttribute("authenticated").equals(true)) {
           resultPage = mainPageRedirect;
        }

        return resultPage;
    }
}
