package org.poliakov.conferencium.command;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.dao.conference.MysqlConferenceDaoImpl;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.model.conference.ConferenceSearchFilters;
import org.poliakov.conferencium.model.conference.ConferenceSortType;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.conference.ConferenceService;
import org.poliakov.conferencium.service.conference.ConferenceServiceImpl;
import org.poliakov.conferencium.util.Page;
import org.poliakov.conferencium.util.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class GetConferencesPageCommand implements ServletCommand {
    private static final Logger LOGGER = Logger.getLogger(GetConferencesPageCommand.class);

    private final ConferenceService conferenceService;
    private final RequestParser requestParser;

    private final String conferencesPage;

    public GetConferencesPageCommand() {
        LOGGER.info("Starting GetConferencesPageCommand");

        conferenceService = new ConferenceServiceImpl(MysqlConferenceDaoImpl.getInstance());
        requestParser = new RequestParser();

        conferencesPage = PageMappingProperties.CONFERENCES_PAGE;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response, String[] params) {
        ConferenceSearchFilters filters = requestParser.parseFilters(request);
        Page<Conference> page = conferenceService.findAll(filters);
        request.setAttribute("page", page);
        request.setAttribute("filters", filters);
        request.setAttribute("orderbyOptions", Arrays.stream(ConferenceSortType.values()).map(Enum::toString).toArray());
        return conferencesPage;
    }
}
