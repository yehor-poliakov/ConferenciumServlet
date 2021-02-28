package org.poliakov.conferencium.command.presentation;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ModeratorServletCommand;
import org.poliakov.conferencium.dao.user.MysqlUserDaoImpl;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.model.presentation.PresentationBuilder;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.user.UserService;
import org.poliakov.conferencium.service.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class GetCreatePresentationPageCommand extends ModeratorServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetCreatePresentationPageCommand.class);

    private final String page;
    private final UserService userService;

    public GetCreatePresentationPageCommand() {
        LOGGER.info("Starting GetCreateConferencePageCommand");
        userService = new UserServiceImpl(MysqlUserDaoImpl.getInstance());
        page = PageMappingProperties.CREATE_PRESENTATION_PAGE;
    }

    @Override
    protected String restrictedExecute(HttpServletRequest request, HttpServletResponse response, String[] params) {
        Long conferenceId = Long.parseLong(params[0]);
        Presentation presentation = new PresentationBuilder().setConferenceId(conferenceId).build();
        request.setAttribute("presentation", presentation);
        Map<Long, String> speakers = userService.findAllSpeakersIdAndNames();
        request.setAttribute("speakers", speakers);
        return page;
    }
}
