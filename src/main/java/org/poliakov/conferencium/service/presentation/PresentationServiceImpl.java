package org.poliakov.conferencium.service.presentation;

import org.apache.log4j.Logger;
import org.poliakov.conferencium.dao.presentation.PresentationDao;
import org.poliakov.conferencium.model.presentation.Presentation;
import org.poliakov.conferencium.model.presentation.PresentationDetails;
import org.poliakov.conferencium.util.Page;

import java.util.List;

public class PresentationServiceImpl implements PresentationService {
    private static final Logger LOGGER = Logger.getLogger(PresentationServiceImpl.class);

    private PresentationDao presentationDao;

    public PresentationServiceImpl(PresentationDao presentationDao) {
        LOGGER.info("Starting PresentationServiceImpl");

        this.presentationDao = presentationDao;
    }

    @Override
    public Presentation createPresentation(Presentation presentation) {
        LOGGER.info("Creating new presentation");
        if (presentation.getSpeakerId() == null) {
            presentation.setSpeakerApproved(false);
        }
        return presentationDao.createPresentation(presentation);
    }

    @Override
    public Presentation updatePresentation(Presentation presentation) {
        LOGGER.info("Updating presentation " + presentation.getId());
        if (presentation.getSpeakerId() == null) {
            presentation.setSpeakerApproved(false);
        }
        return presentationDao.updatePresentation(presentation);
    }

    @Override
    public void deletePresentation(Long id) {
        LOGGER.info("Deleting presentation " + id);
        presentationDao.deletePresentation(id);
    }

    @Override
    public Presentation findPresentationById(Long id) {
        LOGGER.info("Getting presentation " + id);
        return presentationDao.findPresentationById(id);
    }

    @Override
    public Page<PresentationDetails> findAllBySpeakerId(Long speakerId, Integer pageNumber, Integer pageSize) {
        LOGGER.info("Getting presentations by speaker " + speakerId);
        List<PresentationDetails> presentations = presentationDao.findAllBySpeakerId(speakerId, pageNumber, pageSize);
        Integer count = presentationDao.countBySpeaker(speakerId);

        Page<PresentationDetails> page = new Page<>(presentations, pageNumber, pageSize, count);
        return page;
    }

    @Override
    public List<Presentation> findAllByConferenceId(Long conferenceId) {
        LOGGER.info("Getting presentations by conference " + conferenceId);
        List<Presentation> presentations = presentationDao.findAllByConferenceId(conferenceId);
        return presentations;
    }
}
