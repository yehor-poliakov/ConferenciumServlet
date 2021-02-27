package org.poliakov.conferencium.service.user;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.poliakov.conferencium.dao.user.UserDao;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.model.user.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        LOGGER.info("Starting UserServiceImpl");

        this.userDao = userDao;
    }

    @Override
    public boolean registerUser(User user) {
        LOGGER.info("New user registration");
        String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
        user.setPassword(hash);
        return user != null && userDao.createUser(user).getId() != null;
    }

    @Override
    public User getUserByCredentials(String email, String password) {
        LOGGER.info("Getting user by credentials");

        if(email == null || password == null) {
            return null;
        }

        User user = userDao.findUserByEmail(email);

        if (BCrypt.checkpw(password, user.getPassword())) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User findUserByEmail(String email) {
        LOGGER.info("Finding user by email " + email);

        if(email == null) {
            return null;
        }

        return userDao.findUserByEmail(email);
    }

    @Override
    public User findUserById(Long id) {
        LOGGER.info("Finding user by id " + id);

        if(id == null) {
            return null;
        }

        return userDao.findUserById(id);
    }

    @Override
    public boolean isParticipant(Long userId, Long conferenceId) {
        if (userId == null) {
            return false;
        }
        boolean isParticipant = userDao.isParticipant(userId, conferenceId);
        return isParticipant;
    }
}
