package com.lig.ConferenceSessionTrackAPI.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session {

    private List<Talk> talks;
    private LocalTime startTime;
    private LocalTime endTime;

    public Session(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.talks = new ArrayList<>();
    }

    public boolean addTalk(Talk talk) {
        if (talkFitsInSession(talk)) {
            talks.add(talk);
            return true;
        }
        return false;
    }

    private boolean talkFitsInSession(Talk talk) {
        return getTotalDuration() + talk.getDuration() <= getDurationLimit();
    }

    private int getDurationLimit() {
        return (int) (endTime.toSecondOfDay() - startTime.toSecondOfDay()) / 60;
    }

    public int getTotalDuration() {
        return talks.stream().mapToInt(Talk::getDuration).sum();
    }



    public String printSchedule() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a").withLocale(Locale.US);
        LocalTime currentTime = startTime;
        for (Talk talk : talks) {
            sb.append(currentTime.format(formatter));
            sb.append(" ");
            sb.append(talk.getTitle());
            sb.append(" - ");
            sb.append(talk.getDuration());
            sb.append(" minutes\n");
            currentTime = currentTime.plusMinutes(talk.getDuration());
        }
        if (endTime.equals(LocalTime.of(12, 0))) {
            sb.append("12:00PM Lunch\n");
        } else if (endTime.equals(LocalTime.of(17, 0))) {
            sb.append("05:00PM Networking Event\n");
        }
        return sb.toString();
    }
}
