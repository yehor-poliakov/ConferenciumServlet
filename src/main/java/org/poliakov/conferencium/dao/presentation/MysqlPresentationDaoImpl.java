package org.poliakov.conferencium.dao.presentation;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.connection.ConnectionPool;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.model.presentation.PresentationBuilder;
import org.poliakov.conferencium.model.presentation.PresentationDetails;
import org.poliakov.conferencium.model.presentation.PresentationDetailsBuilder;
import org.poliakov.conferencium.properties.MysqlQueryProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlPresentationDaoImpl implements PresentationDao {
    private static final Logger LOGGER = Logger.getLogger(MysqlPresentationDaoImpl.class);

    private static MysqlPresentationDaoImpl INSTANCE;
    private static ConnectionPool connectionPool;

    private static String createQuery;
    private static String updateQuery;
    private static String deleteQuery;
    private static String findByIdQuery;
    private static String findBySpeakerQuery;
    private static String findByConferenceQuery;
    private static String countBySpeakerQuery;
    private static String countByConferenceQuery;


    private MysqlPresentationDaoImpl() {
        LOGGER.info("Starting MysqlPresentationDaoImpl");

        connectionPool = ConnectionPool.getInstance();
        MysqlQueryProperties properties = MysqlQueryProperties.getInstance();

        createQuery = properties.getProperty("createPresentation");
        updateQuery = properties.getProperty("updatePresentation");
        deleteQuery = properties.getProperty("deletePresentation");
        findByIdQuery = properties.getProperty("findPresentationById");
        findBySpeakerQuery = properties.getProperty("findPresentationsBySpeakerId");
        findByConferenceQuery = properties.getProperty("findPresentationsByConferenceId");
        countBySpeakerQuery = properties.getProperty("countPresentationsBySpeakerId");
        countByConferenceQuery = properties.getProperty("countPresentationsByConferenceId");
    }

    public static MysqlPresentationDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MysqlPresentationDaoImpl();
        }
        return INSTANCE;
    }

    @Override
    public Presentation createPresentation(Presentation presentation) {
        LOGGER.info("Creating new presentation");

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, presentation.getConferenceId());
            statement.setLong(2, presentation.getSpeakerId());
            statement.setString(3, presentation.getTime().toString());
            statement.setString(4, presentation.getTopic());
            statement.setBoolean(5, presentation.isSpeakerApproved());
            statement.setBoolean(6, presentation.isPresentationApproved());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                LOGGER.error("Failed to create presentation");
            } else {
                LOGGER.info("Presentation created");

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        presentation.setId(generatedKeys.getLong(1));
                    } else {
                        LOGGER.error("Failed to create presentation without relevant ID");
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return presentation;
    }

    @Override
    public Presentation updatePresentation(Presentation presentation) {
        LOGGER.info("Updating presentation");

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setLong(1, presentation.getSpeakerId());
            statement.setString(2, presentation.getTime().toString());
            statement.setString(3, presentation.getTopic());
            statement.setBoolean(4, presentation.isSpeakerApproved());
            statement.setBoolean(5, presentation.isPresentationApproved());
            statement.setLong(6, presentation.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return presentation;
    }

    @Override
    public void deletePresentation(Long id) {
        LOGGER.info("Deleting presentation " + id);

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public Presentation findPresentationById(Long id) {
        LOGGER.info("Getting presentation with id " + id);
        Presentation presentation = null;

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findByIdQuery);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();

            presentation = getPresentation(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return presentation;
    }

    @Override
    public List<PresentationDetails> findAllBySpeakerId(Long speakerId, Integer pageNumber, Integer pageSize) {
        Integer offset = (pageNumber - 1) * pageSize;

        List<PresentationDetails> res = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findBySpeakerQuery);
            statement.setLong(1, speakerId);
            statement.setInt(2, offset);
            statement.setInt(3, pageSize);

            ResultSet result = statement.executeQuery();
            res = getPresentationDetails(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return res;
    }

    @Override
    public List<Presentation> findAllByConferenceId(Long conferenceId) {
        List<Presentation> res = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(findByConferenceQuery);
            statement.setLong(1, conferenceId);

            ResultSet result = statement.executeQuery();
            res = getPresentations(result);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return res;
    }

    @Override
    public Integer countByConference(Long conferenceId) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(countByConferenceQuery);
            statement.setLong(1, conferenceId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return 0;
    }

    @Override
    public Integer countBySpeaker(Long speakerId) {
        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(countBySpeakerQuery);
            statement.setLong(1, speakerId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }

        return 0;
    }

    private Presentation getPresentation(ResultSet resultSet) {
        Presentation presentation = null;

        try {
            if (resultSet.next()) {
                presentation = new PresentationBuilder()
                        .setId(resultSet.getLong("id"))
                        .setConferenceId(resultSet.getLong("conference_id"))
                        .setTime(resultSet.getTime("time").toLocalTime())
                        .setTopic(resultSet.getString("topic"))
                        .setSpeakerId(resultSet.getLong("speaker_id"))
                        .setSpeakerApproved(resultSet.getBoolean("speaker_approved"))
                        .setPresentationApproved(resultSet.getBoolean("presentation_approved"))
                        .build();
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return presentation;
    }

    private List<Presentation> getPresentations(ResultSet resultSet) {
        List<Presentation> res = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Presentation presentation = new PresentationBuilder()
                        .setId(resultSet.getLong("id"))
                        .setConferenceId(resultSet.getLong("conference_id"))
                        .setTime(resultSet.getTime("time").toLocalTime())
                        .setTopic(resultSet.getString("topic"))
                        .setSpeakerId(resultSet.getLong("speaker_id"))
                        .setSpeaker(resultSet.getString("speaker"))
                        .setSpeakerApproved(resultSet.getBoolean("speaker_approved"))
                        .setPresentationApproved(resultSet.getBoolean("presentation_approved"))
                        .build();
                res.add(presentation);
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return res;
    }

    private List<PresentationDetails> getPresentationDetails(ResultSet resultSet) {
        List<PresentationDetails> res = new ArrayList<>();

        try {
            while (resultSet.next()) {
                PresentationDetails presentation = new PresentationDetailsBuilder()
                        .setConferenceId(resultSet.getLong("conference_id"))
                        .setPresentationTime(resultSet.getTime("time").toLocalTime())
                        .setPresentationTopic(resultSet.getString("topic"))
                        .setSpeakerApproved(resultSet.getBoolean("speaker_approved"))
                        .setPresentationApproved(resultSet.getBoolean("presentation_approved"))
                        .setConferenceTitle(resultSet.getString("title"))
                        .setConferenceLocation(resultSet.getString("location"))
                        .setConferenceDate(resultSet.getDate("date").toLocalDate())
                        .build();
                res.add(presentation);
            }
        } catch (SQLException e) {
            LOGGER.info(e.getMessage());
        }

        return res;
    }
}
