package org.poliakov.conferencium.command.login;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ServletCommand;
import org.poliakov.conferencium.dao.user.MysqlUserDaoImpl;
import org.poliakov.conferencium.model.user.User;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.user.UserService;
import org.poliakov.conferencium.service.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

/**
 * User authentification via POST requests
 */
public class LoginCommand implements ServletCommand {
    private static final Logger LOGGER = Logger.getLogger(LoginCommand.class);

    private final UserService userService;

    private final String loginPage;
    private final String mainPageRedirect;

    public LoginCommand(){
        LOGGER.info("Starting LoginCommand");

        userService = new UserServiceImpl(MysqlUserDaoImpl.getInstance());

        loginPage = PageMappingProperties.LOGIN_PAGE;
        mainPageRedirect = PageMappingProperties.MAIN_PAGE_REDIRECT;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String[] params) {
        LOGGER.info("Executing command");

        String resultPage = loginPage;

        if (request.getParameter("email") != null && request.getParameter("password") != null) {
            User user = userService.getUserByCredentials(request.getParameter("email"),
                    request.getParameter("password"));

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("email", user.getEmail());
                session.setAttribute("id", user.getId());
                session.setAttribute("username", user.getFirstName() + " " + user.getLastName());
                session.setAttribute("authenticated", true);
                session.setAttribute("role", user.getRole().toString());

                HashSet<String> users = (HashSet<String>)request.getServletContext().getAttribute("loggedUsers");
                users.add(user.getEmail());

                resultPage = mainPageRedirect;
            } else {
                request.setAttribute("loginSuccess", false);
            }
        }

        return resultPage;
    }
}
