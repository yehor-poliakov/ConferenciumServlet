package org.poliakov.conferencium.commands.conference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.poliakov.conferencium.command.conference.CreateConferenceCommand;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.model.conference.ConferenceBuilder;
import org.poliakov.conferencium.properties.PageMappingProperties;
import org.poliakov.conferencium.service.conference.ConferenceService;
import org.poliakov.conferencium.util.RequestParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

class CreateConferenceCommandTest {
    @Mock
    private ConferenceService conferenceService;
    @Mock
    private RequestParser requestParser;

    private CreateConferenceCommand createConferenceCommand;

    @Before
    public void init() {
        createConferenceCommand = new CreateConferenceCommand(conferenceService, requestParser);
    }

    @Test
    public void shouldRedirectToConferencesPage() {
        // arrange
        LocalDate date = LocalDate.of(2021, 1, 5);
        Conference conference = new ConferenceBuilder()
                .setDate(date)
                .setLocation("Khakiv")
                .setTitle("Java")
                .build();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String[] params = new String[]{};
        when(requestParser.parseConference(request)).thenReturn(conference);
        when(conferenceService.createConference(conference)).thenReturn(conference);

        // act
        String actual = createConferenceCommand.restrictedExecute(request, response, params);

        // assert
        verify(requestParser, times(1)).parseConference(any());
        verify(conferenceService, times(1)).createConference(any());
        Assert.assertEquals(PageMappingProperties.MAIN_PAGE_REDIRECT, actual);

    }
}