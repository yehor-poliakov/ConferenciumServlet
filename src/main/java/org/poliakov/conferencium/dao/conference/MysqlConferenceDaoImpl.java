package org.poliakov.conferencium.dao.conference;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.connection.ConnectionPool;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.model.conference.ConferenceBuilder;
import org.poliakov.conferencium.model.conference.ConferenceSearchFilters;
import org.poliakov.conferencium.model.conference.ConferenceSortType;
import org.poliakov.conferencium.properties.MysqlQueryProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlConferenceDaoImpl implements ConferenceDao {
    private static final Logger LOGGER = Logger.getLogger(MysqlConferenceDaoImpl.class);

    private static MysqlConferenceDaoImpl INSTANCE;
    private static ConnectionPool connectionPool;

    private static String createQuery;
    private static String updateQuery;
    private static String deleteQuery;
    private static String findByIdQuery;
    private static String findAllQuery;
    private static String countAllQuery;

    private MysqlConferenceDaoImpl() {
        LOGGER.info("Starting MysqlConferenceDaoImpl");

        connectionPool = ConnectionPool.getInstance();
        MysqlQueryProperties properties = MysqlQueryProperties.getInstance();

        createQuery = properties.getProperty("createConference");
        updateQuery = properties.getProperty("updateConference");
        deleteQuery = properties.getProperty("deleteConference");
        findByIdQuery = properties.getProperty("findConferenceById");
        findAllQuery = properties.getProperty("findAllConferences");
        countAllQuery = properties.getProperty("countAllConferences");
    }

    public static MysqlConferenceDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MysqlConferenceDaoImpl();
        }
        return INSTANCE;
    }

    @Override
    public Conference createConference(Conference conference) {
        LOGGER.info("Creating new conference");

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, conference.getDate().toString());
            statement.setString(2, conference.getLocation());
            statement.setString(3, conference.getTitle());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                LOGGER.error("Failed to create conference");
            } else {
                LOGGER.info("Conference created");

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        conference.setId(generatedKeys.getLong(1));
                    } else {
                        LOGGER.error("Failed to create conference without relevant ID");
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return conference;
    }

    @Override
    public Conference updateConference(Conference conference) {
        LOGGER.info("Updating conference");

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, conference.getDate().toString());
            statement.setString(2, conference.getLocation());
            statement.setString(3, conference.getTitle());
            statement.setString(4, conference.getId().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return conference;
    }

    @Override
    public void deleteConference(Long id) {
        LOGGER.info("Deleting conference " + id);

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public Conference findConferenceById(Long id) {
        LOGGER.info("Getting conference with id " + id);
        Conference conference = null;

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findByIdQuery);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            conference = getConference(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return conference;
    }

    private String sortTypeToString(ConferenceSortType sortType) {
        switch (sortType) {
            case DateAsc:
                return "Date ASC";
            case DateDesc:
                return "Date DESC";
            case ParticipantsAsc:
                return "participants_counts.participants ASC";
            case ParticipantsDesc:
                return "participants_counts.participants DESC";
            case PresentationsAsc:
                return "presentations_counts.presentations ASC";
            case PresentationsDesc:
                return "presentations_counts.presentations DESC";
            default:
                return "";
        }
    }

    @Override
    public List<Conference> findAll(ConferenceSearchFilters filters) {
        Integer offset = (filters.getPageNumber() - 1) * filters.getPageSize();

        List<Conference> res = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {
            String query = findAllQuery;
            query += " ORDER BY " + this.sortTypeToString(filters.getOrderby());
            query += " LIMIT " + offset + ", " + filters.getPageSize();

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBoolean(1, filters.isShowPast());
            statement.setBoolean(2, filters.isShowFuture());

            ResultSet result = statement.executeQuery();
            res = getConferences(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return res;
    }

    @Override
    public Integer count(ConferenceSearchFilters filters) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(countAllQuery);
            statement.setBoolean(1, filters.isShowPast());
            statement.setBoolean(2, filters.isShowFuture());

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return 0;
    }

    private Conference getConference(ResultSet resultSet) {
        List<Conference> conferences = getConferences(resultSet);
        if (conferences.size() == 1) {
            return conferences.get(0);
        } else {
            return null;
        }
    }

    private List<Conference> getConferences(ResultSet resultSet) {
        List<Conference> res = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Conference conference = new ConferenceBuilder()
                        .setId(resultSet.getLong("id"))
                        .setDate(resultSet.getDate("date").toLocalDate())
                        .setLocation(resultSet.getString("location"))
                        .setTitle(resultSet.getString("title"))
                        .setParticipantsCount(resultSet.getInt("participants"))
                        .setPresentationsCount(resultSet.getInt("presentations"))
                        .build();
                res.add(conference);
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return res;
    }
}
