package org.poliakov.conferencium.command.presentation;

import org.poliakov.conferencium.command.SpeakerServletCommand;
import org.poliakov.conferencium.dao.presentation.MysqlPresentationDaoImpl;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.presentation.PresentationService;
import org.poliakov.conferencium.service.presentation.PresentationServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SuggestSpeakerCommand extends SpeakerServletCommand {

    private final PresentationService presentationService;

    public SuggestSpeakerCommand() {
        presentationService = new PresentationServiceImpl(MysqlPresentationDaoImpl.getInstance());
    }

    @Override
    protected String restrictedExecute(HttpServletRequest request, HttpServletResponse response, String[] params) {
        Long presentationId = Long.parseLong(params[0]);
        Long speakerId = (Long)request.getSession().getAttribute("id");

        Presentation presentation = presentationService.findPresentationById(presentationId);

        if (presentation.getSpeakerId() == 0) {
            presentation.setSpeakerId(speakerId);
            presentationService.updatePresentation(presentation);
        }

        return PageMappingProperties.CONFERENCE_REDIRECT + presentation.getConferenceId();
    }
}
