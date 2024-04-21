package com.lig.ConferenceSessionTrackAPI.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


public interface ConferenceScheduleService {

    ResponseEntity<?> processExcelFile(MultipartFile file);
}
