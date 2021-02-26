package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.properties.PageMappingProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetMainPageCommand implements ServletCommand {

    private static final Logger LOGGER = Logger.getLogger(GetMainPageCommand.class);

    private static String page;

    public GetMainPageCommand(){
        LOGGER.info("Starting GetMainPageCommand");
        page = PageMappingProperties.MAIN_PAGE;
    }

    public String execute(HttpServletRequest request, HttpServletResponse response, String[] ...params) {
        LOGGER.info("Executing command");
        return page;
    }
}
