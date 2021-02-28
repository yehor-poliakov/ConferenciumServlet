package org.poliakov.conferencium.command.presentation;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ModeratorServletCommand;
import org.poliakov.conferencium.dao.presentation.MysqlPresentationDaoImpl;
import org.poliakov.conferencium.dao.user.MysqlUserDaoImpl;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.presentation.PresentationService;
import org.poliakov.conferencium.service.presentation.PresentationServiceImpl;
import org.poliakov.conferencium.service.user.UserService;
import org.poliakov.conferencium.service.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class GetEditPresentationPageCommand extends ModeratorServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetEditPresentationPageCommand.class);

    private final PresentationService presentationService;
    private final UserService userService;
    private final String page;

    public GetEditPresentationPageCommand() {
        LOGGER.info("Starting GetEditConferencePageCommand");
        presentationService = new PresentationServiceImpl(MysqlPresentationDaoImpl.getInstance());
        userService = new UserServiceImpl(MysqlUserDaoImpl.getInstance());
        page = PageMappingProperties.EDIT_PRESENTATION_PAGE;
    }

    @Override
    protected String restrictedExecute(HttpServletRequest request, HttpServletResponse response,
                                       String[] params) {
        Long presentationId = Long.parseLong(params[0]);
        Presentation presentation = presentationService.findPresentationById(presentationId);
        request.setAttribute("presentation", presentation);
        Map<Long, String> speakers = userService.findAllSpeakersIdAndNames();
        request.setAttribute("speakers", speakers);
        return page;
    }
}
