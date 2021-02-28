package org.poliakov.conferencium.command;

import org.poliakov.conferencium.model.user.UserRole;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.util.Auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public abstract class RestrictedServletCommand implements ServletCommand {
    protected abstract UserRole[] getAllowedRoles();
    protected abstract String restrictedExecute(HttpServletRequest request, HttpServletResponse response, String[] params);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String[] params) {
        UserRole[] roles = getAllowedRoles();

        if (Arrays.stream(roles).noneMatch(role -> Auth.hasRole(request, role))) {
            request.setAttribute("loginSuccess", false);
            return PageMappingProperties.LOGIN_PAGE;
        }

        return restrictedExecute(request, response, params);
    }
}
