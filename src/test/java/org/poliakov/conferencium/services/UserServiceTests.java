package org.poliakov.conferencium.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.poliakov.conferencium.dao.user.UserDao;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.model.user.User;
import org.poliakov.conferencium.service.user.UserService;
import org.poliakov.conferencium.service.user.UserServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {
    @Mock
    private UserDao userDao;
    private UserService userService;

    @Before
    public void init() {
        userService = new UserServiceImpl(userDao);
    }

    @Test
    public void shouldCreateUser() {
        // arrange
        User user = new User();
        user.setId(123L);
        user.setPassword("pass");

        List<User> db = new ArrayList<>();
        when(userDao.createUser(any())).thenAnswer(invocation -> {
            User param = invocation.getArgument(0);
            db.add(param);
            return param;
        });

        // act
        boolean actual = userService.registerUser(user);

        // assert
        verify(userDao, only()).createUser(any());
        Assert.assertTrue(BCrypt.checkpw("pass", user.getPassword()));
        Assert.assertEquals(1, db.toArray().length);
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldGetUserByCredentials() {
        // arrange
        User user = new User();
        user.setId(123L);
        String password = "pass";
        String email = "speaker2@gmail.com";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(10));
        user.setPassword(hash);
        user.setEmail(email);

        when(userDao.findUserByEmail(email)).thenReturn(user);

        // act
        User actual = userService.getUserByCredentials(email, password);

        // assert
        verify(userDao, only()).findUserByEmail(email);
        Assert.assertEquals(user, actual);
    }

    @Test
    public void shouldCheckPasswordGetUserByCredentials() {
        // arrange
        User user = new User();
        user.setId(123L);
        String password = "pass";
        String wrongPassword = "pass1";
        String email = "speaker2@gmail.com";
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(10));
        user.setPassword(hash);
        user.setEmail(email);

        when(userDao.findUserByEmail(email)).thenReturn(user);

        // act
        User actual = userService.getUserByCredentials(email, wrongPassword);

        // assert
        verify(userDao, only()).findUserByEmail(email);
        Assert.assertNull(actual);
    }

    @Test
    public void shouldCheckIsParticipant() {
        // arrange
        Long userId = 1L;
        Long conferenceId = 2L;
        when(userDao.isParticipant(userId, conferenceId)).thenReturn(true);

        // act
        Boolean actual = userService.isParticipant(userId, conferenceId);

        // assert
        verify(userDao, only()).isParticipant(userId, conferenceId);
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldDetectWrongIsParticipant() {
        // arrange
        Long userId = 1L;
        Long wrongConferenceId = 3L;
        when(userDao.isParticipant(userId, wrongConferenceId)).thenReturn(false);

        // act
        Boolean actual = userService.isParticipant(userId, wrongConferenceId);

        // assert
        verify(userDao, only()).isParticipant(userId, wrongConferenceId);
        Assert.assertFalse(actual);
    }

    @Test
    public void shouldFindUserById() {
        // arrange
        User user = new User();
        Long userId = 123L;
        user.setId(userId);

        when(userDao.findUserById(user.getId())).thenReturn(user);

        // act
        User actual = userService.findUserById(userId);

        // assert
        verify(userDao, only()).findUserById(any());
        Assert.assertEquals(user, actual);
    }

    @Test
    public void shouldDetectWrongFindUserById() {
        // arrange
        User user = new User();
        Long wrongUserId = 124L;
        user.setId(wrongUserId);

        // act
        User actual = userService.findUserById(wrongUserId);

        // assert
        verify(userDao, only()).findUserById(any());
        Assert.assertNull(actual);
    }

    @Test
    public void shouldFindUserByEmail() {
        // arrange
        User user = new User();
        Long userId = 123L;
        String userEmail = "user@gmail.com";
        user.setId(userId);
        user.setEmail(userEmail);
        when(userDao.findUserByEmail(user.getEmail())).thenReturn(user);

        // act
        User actual = userService.findUserByEmail(userEmail);

        // assert
        verify(userDao, only()).findUserByEmail(any());
        Assert.assertEquals(user, actual);
    }

    @Test
    public void shouldDetectWrongMailInFindUserByEmail() {
        // arrange
        User user = new User();
        Long userId = 123L;
        String wrongUserEmail = "user2@gmail.com";
        user.setId(userId);
        user.setEmail(wrongUserEmail);

        // act
        User actual = userService.findUserByEmail(wrongUserEmail);

        // assert
        verify(userDao, only()).findUserByEmail(any());
        Assert.assertNull(actual);
    }

    @Test
    public void shouldRegisterUserForConference() {
        // arrange
        User user = new User();
        Long userId = 123L;
        Long conferenceId = 1L;
        String userEmail = "user@gmail.com";
        user.setId(userId);
        user.setEmail(userEmail);
        when(userDao.registerUserForConference(userId, conferenceId)).thenReturn(true);

        // act
        Boolean actual = userService.registerUserForConference(userId, conferenceId);

        // assert
        verify(userDao, only()).registerUserForConference(any(), any());
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldDetectFailureToRegisterUserForConference() {
        // arrange
        Long userId = 123L;
        Long wrongUserId = 124L;
        Long conferenceId = 1L;
        when(userDao.registerUserForConference(wrongUserId, conferenceId)).thenReturn(false);

        // act
        Boolean actual = userService.registerUserForConference(wrongUserId, conferenceId);

        // assert
        verify(userDao, only()).registerUserForConference(any(), any());
        Assert.assertFalse(actual);
    }

    @Test
    public void shouldFindAllSpeakersIdAndNames() {
        // arrange
        Map<Long, String> db = new HashMap<Long, String>(){};
        db.put(1L, "speaker1");
        db.put(2L, "speaker2");
        when(userDao.findAllSpeakersIdAndNames()).thenReturn(db);

        // act
        Map<Long, String> actual = userService.findAllSpeakersIdAndNames();

        // assert
        verify(userDao, only()).findAllSpeakersIdAndNames();
        Assert.assertEquals(db, actual);
    }


    @Test
    public void shouldUnregisterUserFromConference() {
        // arrange
        User user = new User();
        Long userId = 123L;
        Long conferenceId = 1L;
        String userEmail = "user@gmail.com";
        user.setId(userId);
        user.setEmail(userEmail);
        when(userDao.unregisterUserFromConference(userId, conferenceId)).thenReturn(true);

        // act
        Boolean actual = userService.unregisterUserFromConference(userId, conferenceId);

        // assert
        verify(userDao, only()).unregisterUserFromConference(any(), any());
        Assert.assertTrue(actual);
    }

    @Test
    public void shouldDetectFailureToUnregisterUserFromConference() {
        // arrange
        Long userId = 123L;
        Long wrongUserId = 124L;
        Long conferenceId = 1L;
        when(userDao.unregisterUserFromConference(wrongUserId, conferenceId)).thenReturn(false);

        // act
        Boolean actual = userService.unregisterUserFromConference(wrongUserId, conferenceId);

        // assert
        verify(userDao, only()).unregisterUserFromConference(any(), any());
        Assert.assertFalse(actual);
    }
}