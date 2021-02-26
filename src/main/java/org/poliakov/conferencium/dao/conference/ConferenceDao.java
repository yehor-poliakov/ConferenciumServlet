package org.poliakov.conferencium.dao.conference;

import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.model.conference.ConferenceSearchFilters;

import java.util.List;

public interface ConferenceDao {
    Conference createConference(Conference conference);
    Conference updateConference(Conference conference);
    void deleteConference(Long id);
    Conference findConferenceById(Long id);
    List<Conference> findAll(ConferenceSearchFilters filters);
    Integer count(ConferenceSearchFilters filters);
}
