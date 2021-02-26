package org.poliakov.conferencium.service.presentation;

import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.model.presentation.PresentationDetails;
import org.poliakov.conferencium.util.Page;

import java.util.List;

public interface PresentationService {
    Presentation createPresentation(Presentation presentation);
    Presentation updatePresentation(Presentation presentation);
    void deletePresentation(Long id);
    Presentation findPresentationById(Long id);
    Page<PresentationDetails> findAllBySpeakerId(Long speakerId, Integer pageNumber, Integer pageSize);
    List<Presentation> findAllByConferenceId(Long conferenceId);
}
