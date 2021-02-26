package org.poliakov.conferencium.model.conference;

import java.time.LocalDate;

public class ConferenceBuilder {
    private Long id;
    private String title;
    private String location;
    private LocalDate date;
    private Integer actualParticipantsCount;
    private Integer presentationsCount;
    private Integer participantsCount;

    public ConferenceBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ConferenceBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ConferenceBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public ConferenceBuilder setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public ConferenceBuilder setActualParticipantsCount(Integer actualParticipantsCount) {
        this.actualParticipantsCount = actualParticipantsCount;
        return this;
    }

    public ConferenceBuilder setPresentationsCount(Integer presentationsCount) {
        this.presentationsCount = presentationsCount;
        return this;
    }

    public ConferenceBuilder setParticipantsCount(Integer participantsCount) {
        this.participantsCount = participantsCount;
        return this;
    }

    public Conference build() {
        return new Conference(id, title, location, date, actualParticipantsCount, participantsCount, presentationsCount);
    }
}
