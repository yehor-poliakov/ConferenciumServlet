package org.poliakov.conferencium.command.presentation;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ModeratorServletCommand;
import org.poliakov.conferencium.dao.presentation.MysqlPresentationDaoImpl;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.presentation.PresentationService;
import org.poliakov.conferencium.service.presentation.PresentationServiceImpl;
import org.poliakov.conferencium.util.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeletePresentationCommand extends ModeratorServletCommand {
    private static final Logger LOGGER = Logger.getLogger(DeletePresentationCommand.class);

    private final PresentationService presentationService;
    private final String conferencePageRedirect;

    public DeletePresentationCommand() {
        LOGGER.info("Starting GetCreateConferencePageCommand");

        presentationService = new PresentationServiceImpl(MysqlPresentationDaoImpl.getInstance());
        conferencePageRedirect = PageMappingProperties.CONFERENCE_REDIRECT;
    }

    @Override
    protected String restrictedExecute(HttpServletRequest request, HttpServletResponse response, String[] params) {
        Long presentationId = Long.parseLong(params[0]);
        presentationService.deletePresentation(presentationId);

        long conferenceId = Long.parseLong(request.getParameter("conferenceId"));
        return conferencePageRedirect + conferenceId;
    }
}
