package org.poliakov.conferencium.util;

import org.poliakov.conferencium.model.user.UserRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Auth {
    public static boolean hasRole(HttpServletRequest request, UserRole role) {
        HttpSession session = request.getSession();
        Object authenticated = request.getSession().getAttribute("authenticated");
        Object userRole = request.getSession().getAttribute("role");

        if (authenticated == null || authenticated.equals(false) || !userRole.equals(role.toString())) {
            return false;
        }

        return true;
    }
}
