package org.poliakov.conferencium.model.conference;

import java.time.LocalDate;

public class Conference {
    private Long id;
    private String title;
    private String location;
    private LocalDate date;
    private Integer actualParticipantsCount;
    private Integer presentationsCount;
    private Integer participantsCount;

    public Conference() {
    }

    public Conference(Long id, String title, String location, LocalDate date, Integer actualParticipantsCount, Integer participantsCount, Integer presentationsCount) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.date = date;
        this.actualParticipantsCount = actualParticipantsCount;
        this.participantsCount = participantsCount;
        this.presentationsCount = presentationsCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getActualParticipantsCount() {
        return actualParticipantsCount;
    }

    public void setActualParticipantsCount(Integer actualParticipantsCount) {
        this.actualParticipantsCount = actualParticipantsCount;
    }

    public Integer getParticipantsCount() {
        return participantsCount;
    }

    public void setParticipantsCount(Integer participantsCount) {
        this.participantsCount = participantsCount;
    }

    public Integer getPresentationsCount() {
        return presentationsCount;
    }

    public void setPresentationsCount(Integer presentationsCount) {
        this.presentationsCount = presentationsCount;
    }
}
