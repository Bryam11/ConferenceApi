package com.lig.ConferenceSessionTrackAPI.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class SessionTest {
    private Session session;
    private Talk talk;

    @BeforeEach
    void setUp() {
        session = new Session(LocalTime.of(9, 0), LocalTime.of(17, 0));
        talk = Mockito.mock(Talk.class);
    }

    @Test
    void addTalk() {
        Mockito.when(talk.getDuration()).thenReturn(30);
        assertTrue(session.addTalk(talk));
        assertEquals(30, session.getTotalDuration());
    }

    @Test
    void addTalkExceedingLimit() {
        Mockito.when(talk.getDuration()).thenReturn(480);
        assertTrue(session.addTalk(talk));
        assertEquals(480, session.getTotalDuration());
    }
}