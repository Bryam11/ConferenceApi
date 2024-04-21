package com.lig.ConferenceSessionTrackAPI.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TrackTest {

    private Track track;
    private Session session;
    private Talk talk;

    @BeforeEach
    void setUp() {
        track = new Track();
        session = Mockito.mock(Session.class);
        talk = Mockito.mock(Talk.class);
    }

    @Test
    void addSession() {
        track.addSession(session);
        assertTrue(track.getSessions().contains(session));
    }

    @Test
    void addTalk() {
        // Configura el mock de Session para que addTalk() devuelva true
        Mockito.when(session.addTalk(talk)).thenReturn(true);

        // Agrega el mock de Session al Track
        track.addSession(session);

        // Ahora, cuando llames a addTalk() en el Track, debería devolver true
        assertTrue(track.addTalk(talk));
    }

    @Test
    void printSchedule() {
        String sessionSchedule = "09:00 AM Talk 1 - 30 minutes\n";
        Mockito.when(session.printSchedule()).thenReturn(sessionSchedule);
        track.addSession(session);
        assertTrue(track.printSchedule().contains(sessionSchedule));
    }
}