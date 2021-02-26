package org.poliakov.conferencium.model.presentation;

import java.time.LocalTime;

public class PresentationBuilder {
    private Long id;
    private String topic;
    private LocalTime time;
    private Long speakerId;
    private Long conferenceId;
    private boolean presentationApproved;
    private boolean speakerApproved;
    private String speaker;

    public PresentationBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public PresentationBuilder setTopic(String topic) {
        this.topic = topic;
        return this;
    }
    public PresentationBuilder setTime(LocalTime time) {
        this.time = time;
        return this;
    }
    public PresentationBuilder setSpeakerId(Long speakerId) {
        this.speakerId = speakerId;
        return this;
    }
    public PresentationBuilder setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
        return this;
    }
    public PresentationBuilder setPresentationApproved(boolean presentationApproved) {
        this.presentationApproved = presentationApproved;
        return this;
    }
    public PresentationBuilder setSpeakerApproved(boolean speakerApproved) {
        this.speakerApproved = speakerApproved;
        return this;
    }

    public PresentationBuilder setSpeaker(String speaker) {
        this.speaker = speaker;
        return this;
    }

    public Presentation build() {
        return new Presentation(id, topic, time, speakerId, conferenceId, presentationApproved, speakerApproved, speaker);
    }
}
