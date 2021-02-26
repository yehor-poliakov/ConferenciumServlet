package org.poliakov.conferencium.model.presentation;

import java.time.LocalTime;

public class Presentation {
    private Long id;
    private String topic;
    private LocalTime time;
    private Long speakerId;
    private Long conferenceId;
    private Boolean presentationApproved;
    private Boolean speakerApproved;
    private String speaker;

    public Presentation(Long id, String topic, LocalTime time, Long speakerId, Long conferenceId, boolean presentationApproved, boolean speakerApproved, String speaker) {
        this.id = id;
        this.topic = topic;
        this.time = time;
        this.speakerId = speakerId;
        this.conferenceId = conferenceId;
        this.presentationApproved = presentationApproved;
        this.speakerApproved = speakerApproved;
        this.speaker = speaker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Long getSpeakerId() {
        return speakerId;
    }

    public void setSpeakerId(Long speakerId) {
        this.speakerId = speakerId;
    }

    public Boolean isPresentationApproved() {
        return presentationApproved;
    }

    public void setPresentationApproved(boolean presentationApproved) {
        this.presentationApproved = presentationApproved;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public Boolean isSpeakerApproved() {
        return speakerApproved;
    }

    public void setSpeakerApproved(boolean speakerApproved) {
        this.speakerApproved = speakerApproved;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }
}
