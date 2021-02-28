package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.dao.conference.MysqlConferenceDaoImpl;
import org.poliakov.conferencium.dao.presentation.MysqlPresentationDaoImpl;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.conference.ConferenceService;
import org.poliakov.conferencium.service.conference.ConferenceServiceImpl;
import org.poliakov.conferencium.service.presentation.PresentationService;
import org.poliakov.conferencium.service.presentation.PresentationServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetEditPresentationPageCommand extends ModeratorServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetEditPresentationPageCommand.class);

    private final PresentationService presentationService;
    private final String page;

    public GetEditPresentationPageCommand() {
        LOGGER.info("Starting GetEditConferencePageCommand");
        presentationService = new PresentationServiceImpl(MysqlPresentationDaoImpl.getInstance());
        page = PageMappingProperties.CREATE_PRESENTATION_PAGE;
    }

    @Override
    protected String moderatorExecute(HttpServletRequest request, HttpServletResponse response,
                                      String[] params) {
        Long presentationId = Long.parseLong(params[0]);
        Presentation presentation = presentationService.findPresentationById(presentationId);
        request.setAttribute("presentation", presentation);
        return page;
    }
}
