package org.poliakov.conferencium.dao.user;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.connection.ConnectionPool;
import org.poliakov.conferencium.model.user.User;
import org.poliakov.conferencium.model.user.UserBuilder;
import org.poliakov.conferencium.model.user.UserRole;
import org.poliakov.conferencium.properties.MysqlQueryProperties;

import java.sql.*;

public class MysqlUserDaoImpl implements UserDao {
    private static final Logger LOGGER = Logger.getLogger(MysqlUserDaoImpl.class);

    private static MysqlUserDaoImpl INSTANCE;
    private static ConnectionPool connectionPool;

    private static String createQuery;
    private static String findByIdQuery;
    private static String findByEmailQuery;
    private static String findByEmailAndPasswordQuery;

    public MysqlUserDaoImpl() {
        LOGGER.info("Starting MysqlUserDaoImpl");

        connectionPool = ConnectionPool.getInstance();
        MysqlQueryProperties properties = MysqlQueryProperties.getInstance();

        createQuery = properties.getProperty("createUser");
        findByIdQuery = properties.getProperty("findUserById");
        findByEmailQuery = properties.getProperty("findUserByEmail");
        findByEmailAndPasswordQuery = properties.getProperty("findUserByEmailAndPassword");
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
