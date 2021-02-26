package org.poliakov.conferencium.service.conference;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.dao.conference.ConferenceDao;
import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.model.conference.ConferenceSearchFilters;
import org.poliakov.conferencium.util.Page;

import java.util.List;

public class ConferenceServiceImpl implements ConferenceService {
    private static final Logger LOGGER = Logger.getLogger(ConferenceServiceImpl.class);

    private ConferenceDao conferenceDao;

    public ConferenceServiceImpl(ConferenceDao conferenceDao) {
        LOGGER.info("Starting ConferenceServiceImpl");
        this.conferenceDao = conferenceDao;
    }

    @Override
    public Conference getConferenceById(Long id) {
        LOGGER.info("Finding conference by id " + id);

        if(id <= 0) {
            return null;
        }

        return conferenceDao.findConferenceById(id);
    }

    @Override
    public Page<Conference> findAll(ConferenceSearchFilters filters) {
        List<Conference> conferences = conferenceDao.findAll(filters);
        Integer count = conferenceDao.count(filters);

        Page<Conference> page = new Page<>(conferences, filters.getPageNumber(), filters.getPageSize(), count);
        return page;
    }

    @Override
    public Conference createConference(Conference conference) {
        LOGGER.info("Creating new conference");
        return conferenceDao.createConference(conference);
    }

    @Override
    public void deleteConference(Long id) {
        LOGGER.info("Deleting conference by id " + id);

        if (id > 0) {
           conferenceDao.deleteConference(id);
        }
    }

    @Override
    public Conference updateConference(Conference conference) {
        LOGGER.info("Updating conference " + conference.getId());
        return conferenceDao.updateConference(conference);
    }
}
