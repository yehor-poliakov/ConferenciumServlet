package org.poliakov.conferencium.dao.user;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.connection.ConnectionPool;
import org.poliakov.conferencium.model.presentation.PresentationDetails;
import org.poliakov.conferencium.model.presentation.PresentationDetailsBuilder;
import org.poliakov.conferencium.model.user.User;
import org.poliakov.conferencium.model.user.UserBuilder;
import org.poliakov.conferencium.model.user.UserRole;
import org.poliakov.conferencium.properties.MysqlQueryProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MysqlUserDaoImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(MysqlUserDaoImpl.class);

    private static MysqlUserDaoImpl INSTANCE;
    private final ConnectionPool connectionPool;

    private final String createQuery;
    private final String findByIdQuery;
    private final String findByEmailQuery;
    private final String findByEmailAndPasswordQuery;
    private final String isParticipantQuery;
    private final String findAllUsersSpeakersQuery;

    public MysqlUserDaoImpl() {
        LOGGER.info("Starting MysqlUserDaoImpl");

        connectionPool = ConnectionPool.getInstance();
        MysqlQueryProperties properties = MysqlQueryProperties.getInstance();

        createQuery = properties.getProperty("createUser");
        findByIdQuery = properties.getProperty("findUserById");
        findByEmailQuery = properties.getProperty("findUserByEmail");
        findByEmailAndPasswordQuery = properties.getProperty("findUserByEmailAndPassword");
        isParticipantQuery = properties.getProperty("isParticipant");
        findAllUsersSpeakersQuery = properties.getProperty("findAllUsersSpeakers");
    }

    public static MysqlUserDaoImpl getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MysqlUserDaoImpl();
        }
        return INSTANCE;
    }

    @Override
    public User createUser(User user) {
        LOGGER.info("Creating new user");

        try(Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole().toString());

            int affectedRows = statement.executeUpdate();

            if(affectedRows == 0) {
                LOGGER.error("Failed to create user");
            }
            else {
                LOGGER.info("User created");

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    }
                    else {
                        LOGGER.error("Failed to create user without relevant ID");
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return user;
    }

    @Override
    public User findUserById(Long id) {
        LOGGER.info("Getting user with id " + id);
        User user = null;

        try(Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findByIdQuery);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            user = getUser(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        LOGGER.info("Getting user with email " + email);
        User user = null;

        try(Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findByEmailQuery);
            statement.setString(1, email);

            ResultSet result = statement.executeQuery();

            user = getUser(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return user;
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        LOGGER.info("Getting user with email " + email);
        User user = null;

        try(Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findByEmailAndPasswordQuery);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            user = getUser(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return user;
    }

    @Override
    public boolean isParticipant(Long userId, Long conferenceId) {
        try(Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(isParticipantQuery);
            statement.setLong(1, userId);
            statement.setLong(2, conferenceId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getBoolean(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return false;
    }

    public Map<Long, String> findAllSpeakersIdAndNames(ResultSet resultSet){
        Map<Long, String> res = new HashMap<>();
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findAllUsersSpeakersQuery);
            ResultSet result = statement.executeQuery();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                String userName = resultSet.getString("user_name");
                res.put(userId, userName);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return res;
    }

    private User getUser(ResultSet resultSet) {
        User user = null;

        try {
            if(resultSet.next()) {
                user = new UserBuilder().setId(resultSet.getLong("id"))
                        .setFirstName(resultSet.getString("first_name"))
                        .setLastName(resultSet.getString("last_name"))
                        .setEmail(resultSet.getString("email"))
                        .setPassword(resultSet.getString("password"))
                        .setRole(UserRole.valueOf(resultSet.getString("role")))
                        .build();
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return user;
    }
}
