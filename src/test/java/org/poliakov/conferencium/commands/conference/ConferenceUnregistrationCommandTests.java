package org.poliakov.conferencium.commands.conference;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.poliakov.conferencium.command.conference.ConferenceUnregistrationCommand;
import org.poliakov.conferencium.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceUnregistrationCommandTests {
    @Mock
    private UserService userService;

    private ConferenceUnregistrationCommand conferenceUnregistrationCommand;

    @Before
    public void init() {
        conferenceUnregistrationCommand = new ConferenceUnregistrationCommand(userService);
    }

    @Test
    public void shouldRedirectToConferencePage() {
        // arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("id")).thenReturn(45L);
        when(request.getSession()).thenReturn(session);
        HttpServletResponse response = mock(HttpServletResponse.class);
        String[] params = new String[] { "9" };

        // act
        String actual = conferenceUnregistrationCommand.restrictedExecute(request, response, params);

        // assert
        Assert.assertEquals("redirect:conference/9", actual);
    }
}
