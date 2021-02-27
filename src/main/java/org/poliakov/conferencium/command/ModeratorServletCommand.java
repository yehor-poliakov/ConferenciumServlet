package org.poliakov.conferencium.command;

import org.poliakov.conferencium.model.user.UserRole;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.util.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ModeratorServletCommand implements ServletCommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String[] params) {
        if (!Auth.hasRole(request, UserRole.MODERATOR)) {
            request.setAttribute("loginSuccess", false);
            return PageMappingProperties.LOGIN_PAGE;
        }

        return moderatorExecute(request, response, params);
    }

    protected abstract String moderatorExecute(HttpServletRequest request, HttpServletResponse response, String[] params);
}
