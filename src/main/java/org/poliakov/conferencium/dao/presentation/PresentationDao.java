package org.poliakov.conferencium.dao.presentation;

import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.model.presentation.PresentationDetails;

import java.util.List;

public interface PresentationDao {
    Presentation createPresentation(Presentation Presentation);
    Presentation updatePresentation(Presentation Presentation);
    void deletePresentation(Long id);
    Presentation findPresentationById(Long id);
    List<PresentationDetails> findAllBySpeakerId(Long speakerId, Integer pageNumber, Integer pageSize);
    List<Presentation> findAllByConferenceId(Long conferenceId);
    Integer countByConference(Long conferenceId);
    Integer countBySpeaker(Long speakerId);
}
