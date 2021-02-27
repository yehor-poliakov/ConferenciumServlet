package org.poliakov.conferencium.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface to be implemented by command classes
 */
public interface ServletCommand {

    /**
     * @param request  Http request
     * @param response Http response
     * @param params   parameters to be parsed by regex
     * @return         view
     */
    String execute(HttpServletRequest request, HttpServletResponse response, String[] params);
}
