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

public class CreatePresentationCommand extends ModeratorServletCommand {
    private static final Logger LOGGER = Logger.getLogger(CreatePresentationCommand.class);

    private final RequestParser requestParser;
    private final PresentationService presentationService;


    private final String page;
    private final String conferencePageRedirect;

    public CreatePresentationCommand() {
        LOGGER.info("Starting CreatePresentationCommand");

        requestParser = new RequestParser();
        presentationService = new PresentationServiceImpl(MysqlPresentationDaoImpl.getInstance());

        page = PageMappingProperties.CREATE_PRESENTATION_PAGE;
        conferencePageRedirect = PageMappingProperties.CONFERENCE_REDIRECT;
    }

    @Override
    protected String restrictedExecute(HttpServletRequest request, HttpServletResponse response, String[] params) {
        Presentation presentation = requestParser.parsePresentation(request);

        Long conferenceId = Long.parseLong(params[0]);
        presentation.setConferenceId(conferenceId);

        boolean error = false;

        if (presentation.getTopic() == null || presentation.getTopic().equals("")) {
            request.setAttribute("topicError", true);
            error = true;
        }

        if (presentation.getTime() == null) {
            request.setAttribute("timeError", true);
            error = true;
        }

        if (error) {
            request.setAttribute("presentation", presentation);
            return page;
        }

        presentationService.createPresentation(presentation);
        return conferencePageRedirect + conferenceId;
    }
}
