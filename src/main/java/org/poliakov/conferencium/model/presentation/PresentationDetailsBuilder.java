package org.poliakov.conferencium.model.presentation;

import java.time.LocalDate;
import java.time.LocalTime;

public class PresentationDetailsBuilder {
    private Long conferenceId;
    private String presentationTopic;
    private String conferenceTitle;
    private String conferenceLocation;
    private LocalDate conferenceDate;
    private LocalTime presentationTime;
    private boolean presentationApproved;
    private boolean speakerApproved;

    public PresentationDetailsBuilder() {
    }

    public PresentationDetailsBuilder setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
        return this;
    }

    public PresentationDetailsBuilder setPresentationTopic(String presentationTopic) {
        this.presentationTopic = presentationTopic;
        return this;
    }

    public PresentationDetailsBuilder setConferenceTitle(String conferenceTitle) {
        this.conferenceTitle = conferenceTitle;
        return this;
    }

    public PresentationDetailsBuilder setConferenceLocation(String conferenceLocation) {
        this.conferenceLocation = conferenceLocation;
        return this;
    }

    public PresentationDetailsBuilder setConferenceDate(LocalDate conferenceDate) {
        this.conferenceDate = conferenceDate;
        return this;
    }

    public PresentationDetailsBuilder setPresentationTime(LocalTime presentationTime) {
        this.presentationTime = presentationTime;
        return this;
    }

    public PresentationDetailsBuilder setPresentationApproved(boolean presentationApproved) {
        this.presentationApproved = presentationApproved;
        return this;
    }

    public PresentationDetailsBuilder setSpeakerApproved(boolean speakerApproved) {
        this.speakerApproved = speakerApproved;
        return this;
    }

    public PresentationDetails build() {
        return new PresentationDetails(conferenceId, presentationTopic, conferenceTitle, conferenceLocation,
                conferenceDate, presentationTime, presentationApproved, speakerApproved);
    }
}
