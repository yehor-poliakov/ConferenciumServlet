package org.poliakov.conferencium.dao.user;

import org.poliakov.conferencium.model.user.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User createUser(User user);

    User findUserById(Long id);

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);

    boolean isParticipant(Long userId, Long conferenceId);

    boolean registerUserForConference(String email, Long conferenceId);

    Map<Long, String> findAllSpeakersIdAndNames();

    boolean unregisterUserFromConference(String email, Long conferenceId);
}
