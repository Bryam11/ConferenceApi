package com.lig.ConferenceSessionTrackAPI.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Track {
    private final List<Session> sessions = new ArrayList<>();

    public void addSession(Session session) {
        sessions.add(session);
    }

    public boolean addTalk(Talk talk) {
        for (Session session : sessions) {
            if (session.addTalk(talk)) {
                return true;
            }
        }
        return false;
    }

    public String printSchedule() {
        StringBuilder sb = new StringBuilder();
        for (Session session : sessions) {
            sb.append(session.printSchedule());
            sb.append("\n"); // Añade una nueva línea entre cada sesión
        }
        return sb.toString();
    }
}
