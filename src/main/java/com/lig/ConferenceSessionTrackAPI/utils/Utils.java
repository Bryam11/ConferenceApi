package com.lig.ConferenceSessionTrackAPI.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static String nameFile() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dateTime = LocalDateTime.now().format(dtf);

        return  "scheduleConference_" + dateTime + ".txt";
    }
}
