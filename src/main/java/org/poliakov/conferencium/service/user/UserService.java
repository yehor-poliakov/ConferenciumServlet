package org.poliakov.conferencium.service.user;

import org.poliakov.conferencium.model.user.User;

public interface UserService {
    boolean registerUser(User user);
    User getUserByCredentials(String email, String password);
    User findUserByEmail(String email);
    User findUserById(Long id);
    boolean isParticipant(Long userId, Long conferenceId);
}
