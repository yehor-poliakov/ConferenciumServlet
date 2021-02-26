package org.poliakov.conferencium.model.presentation;

import java.time.LocalDate;
import java.time.LocalTime;

public class PresentationDetails {
    private Long conferenceId;
    private String presentationTopic;
    private String conferenceTitle;
    private String conferenceLocation;
    private LocalDate conferenceDate;
    private LocalTime presentationTime;
    private boolean presentationApproved;
    private boolean speakerApproved;

    public PresentationDetails(Long conferenceId, String presentationTopic, String conferenceTitle,
                               String conferenceLocation, LocalDate conferenceDate, LocalTime presentationTime,
                               Boolean presentationApproved, Boolean speakerApproved) {
        this.conferenceId = conferenceId;
        this.presentationTopic = presentationTopic;
        this.conferenceTitle = conferenceTitle;
        this.conferenceLocation = conferenceLocation;
        this.conferenceDate = conferenceDate;
        this.presentationTime = presentationTime;
        this.presentationApproved = presentationApproved;
        this.speakerApproved = speakerApproved;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public LocalDate getConferenceDate() {
        return conferenceDate;
    }

    public void setConferenceDate(LocalDate conferenceDate) {
        this.conferenceDate = conferenceDate;
    }

    public LocalTime getPresentationTime() {
        return presentationTime;
    }

    public void setConferenceLocation(String conferenceLocation) {
        this.conferenceLocation = conferenceLocation;
    }

    public String getConferenceLocation() {
        return conferenceLocation;
    }

    public void setConferenceTitle(String conferenceTitle) {
        this.conferenceTitle = conferenceTitle;
    }

    public String getConferenceTitle() {
        return conferenceTitle;
    }

    public void setPresentationApproved(boolean presentationApproved) {
        this.presentationApproved = presentationApproved;
    }

    public String getPresentationTopic() {
        return presentationTopic;
    }

    public void setPresentationTime(LocalTime presentationTime) {
        this.presentationTime = presentationTime;
    }

    public void setPresentationTopic(String presentationTopic) {
        this.presentationTopic = presentationTopic;
    }

    public void setSpeakerApproved(boolean speakerApproved) {
        this.speakerApproved = speakerApproved;
    }

    public boolean isPresentationApproved() {
        return presentationApproved;
    }

    public boolean isSpeakerApproved() {
        return speakerApproved;
    }
}
