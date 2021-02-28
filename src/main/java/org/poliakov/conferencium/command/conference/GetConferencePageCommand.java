package org.poliakov.conferencium.command.conference;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.command.ParticipantServletCommand;
import org.poliakov.conferencium.command.ServletCommand;
import org.poliakov.conferencium.command.UserServletCommand;
import org.poliakov.conferencium.dao.conference.MysqlConferenceDaoImpl;
import org.poliakov.conferencium.dao.presentation.MysqlPresentationDaoImpl;
import org.poliakov.conferencium.dao.user.MysqlUserDaoImpl;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.conference.ConferenceService;
import org.poliakov.conferencium.service.conference.ConferenceServiceImpl;
import org.poliakov.conferencium.service.presentation.PresentationService;
import org.poliakov.conferencium.service.presentation.PresentationServiceImpl;
import org.poliakov.conferencium.service.user.UserService;
import org.poliakov.conferencium.service.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetConferencePageCommand extends UserServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetConferencePageCommand.class);

    private final ConferenceService conferenceService;
    private final PresentationService presentationService;
    private final UserService userService;
    private final String page;

    public GetConferencePageCommand() {
        LOGGER.info("Starting GetConferencePageCommand");

        conferenceService = new ConferenceServiceImpl(MysqlConferenceDaoImpl.getInstance());
        presentationService = new PresentationServiceImpl(MysqlPresentationDaoImpl.getInstance());
        userService = new UserServiceImpl(MysqlUserDaoImpl.getInstance());

        page = PageMappingProperties.CONFERENCE_VIEW_PAGE;
    }

    @Override
    public String restrictedExecute(HttpServletRequest request, HttpServletResponse response, String[] params) {
        Long conferenceId = Long.parseLong(params[0]);
        Long participantId = (Long) request.getSession().getAttribute("id");

        Conference conference = conferenceService.getConferenceById(conferenceId);
        List<Presentation> presentations = presentationService.findAllByConferenceId(conferenceId);
        boolean isRegistered = userService.isParticipant(participantId, conferenceId);

        request.setAttribute("conference", conference);
        request.setAttribute("presentations", presentations);
        request.setAttribute("registered", isRegistered);
        return page;
    }
}
