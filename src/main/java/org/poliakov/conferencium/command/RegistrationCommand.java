package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.dao.user.MysqlUserDaoImpl;
import org.poliakov.conferencium.model.user.User;
import org.poliakov.conferencium.model.user.UserBuilder;
import org.poliakov.conferencium.model.user.UserRole;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.user.UserService;
import org.poliakov.conferencium.service.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User registration via POST requests
 */
public class RegistrationCommand implements ServletCommand {
    private static final Logger LOGGER = Logger.getLogger(RegistrationCommand.class);

    private final UserService userService;

    private final String registrationPage;
    private final String loginPage;

    public RegistrationCommand() {
        LOGGER.info("Starting LoginCommand");

        userService = new UserServiceImpl(MysqlUserDaoImpl.getInstance());

        registrationPage = PageMappingProperties.REGISTRATION_PAGE;
        loginPage = PageMappingProperties.LOGIN_PAGE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String[] params) {
        LOGGER.info("Executing command");
        String resultPage = registrationPage;

        if ((userService.findUserByEmail(request.getParameter("email")) == null)) {
            LOGGER.info("Registering new user");

            User user = new UserBuilder().setEmail(request.getParameter("email"))
                    .setPassword(request.getParameter("password"))
                    .setFirstName(request.getParameter("firstname"))
                    .setLastName(request.getParameter("lastname"))
                    .setRole(stringToRole((request.getParameter("isSpeaker"))))
                    .build();
            userService.registerUser(user);
            resultPage = loginPage;
        } else {
            request.setAttribute("userExists", true);
        }

        return resultPage;
    }

    private UserRole stringToRole (String role){
        return (role.equals("on") ? UserRole.SPEAKER : UserRole.PARTICIPANT);
    }
}
