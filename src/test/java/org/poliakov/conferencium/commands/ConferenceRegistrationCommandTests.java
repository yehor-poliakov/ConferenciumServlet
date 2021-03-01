package org.poliakov.conferencium.commands;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.poliakov.conferencium.command.conference.ConferenceRegistrationCommand;
import org.poliakov.conferencium.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConferenceRegistrationCommandTests {
    @Mock
    private UserService userService;

    private ConferenceRegistrationCommand conferenceRegistrationCommand;

    @Before
    public void init() {
        conferenceRegistrationCommand = new ConferenceRegistrationCommand(userService);
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
        String actual = conferenceRegistrationCommand.restrictedExecute(request, response, params);

        // assert
        Assert.assertEquals("redirect:conference/9", actual);
    }
}
