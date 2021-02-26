package org.poliakov.conferencium.dao.user;

import org.poliakov.conferencium.model.user.User;

public interface UserDao {
    User createUser(User user);
    User findUserById(Long id);
    User findUserByEmail(String email);
    User findUserByEmailAndPassword(String email, String password);
}
