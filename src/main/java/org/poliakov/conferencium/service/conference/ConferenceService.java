package org.poliakov.conferencium.service.conference;

import org.poliakov.conferencium.model.conference.Conference;
import org.poliakov.conferencium.model.conference.ConferenceSearchFilters;
import org.poliakov.conferencium.util.Page;

public interface ConferenceService {
    Conference createConference(Conference conference);
    Conference updateConference(Conference conference);
    void deleteConference(Long id);
    Conference getConferenceById(Long id);
    Page<Conference> findAll(ConferenceSearchFilters filters);
}
