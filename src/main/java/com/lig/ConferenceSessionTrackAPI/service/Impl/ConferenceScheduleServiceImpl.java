package com.lig.ConferenceSessionTrackAPI.service.Impl;

import com.lig.ConferenceSessionTrackAPI.model.Session;
import com.lig.ConferenceSessionTrackAPI.model.Talk;
import com.lig.ConferenceSessionTrackAPI.model.Track;
import com.lig.ConferenceSessionTrackAPI.service.ConferenceScheduleService;
import com.lig.ConferenceSessionTrackAPI.utils.Utils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



@Service
public class ConferenceScheduleServiceImpl implements ConferenceScheduleService {

    public ByteArrayOutputStream generateSchedule(List<Talk> talks) {
        List<Track> tracks = new ArrayList<>();
        while (!talks.isEmpty()) {
            Track track = new Track();
            track.addSession(new Session(LocalTime.of(9, 0), LocalTime.of(12, 0)));
            track.addSession(new Session(LocalTime.of(13, 0), LocalTime.of(17, 0)));

            talks = talks.stream()
                    .filter(talk -> !track.addTalk(talk))
                    .collect(Collectors.toList());

            tracks.add(track);
        }

        IntStream.range(0, tracks.size())
                .forEach(i -> {
                    System.out.println("TRACK " + (i + 1));
                    System.out.println("=========");
                    System.out.println(tracks.get(i).printSchedule());
                });

        return createTextFile(tracks);

    }

    @Override
    public ResponseEntity<?> processExcelFile(MultipartFile file) {
        try {
            InputStream in = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(in);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            List<Talk> talks = new ArrayList<>();

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (currentRow.getRowNum() == 0) { // skip the header row
                    continue;
                }

                Cell titleCell = currentRow.getCell(0);
                Cell durationCell = currentRow.getCell(1);

                String title = titleCell.getStringCellValue();
                if (title.matches(".*\\d.*")) {
                    return ResponseEntity.badRequest().body("El archivo contiene tareas con números en el título: " + title);
                }
                int duration = (int) durationCell.getNumericCellValue();

                talks.add(new Talk(title, duration));
            }

            ByteArrayOutputStream baos = generateSchedule(talks);
            Resource resource = new ByteArrayResource(baos.toByteArray());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + Utils.nameFile() + "\"")
                    .body(resource);
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse Excel file", e);
        }
    }

    private ByteArrayOutputStream createTextFile(List<Track> tracks) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(baos);

        IntStream.range(0, tracks.size())
                .forEach(i -> {
                    writer.println("TRACK " + (i + 1));
                    writer.println("=========");
                    writer.println(tracks.get(i).printSchedule()); // write to file
                });

        writer.flush();
        return baos;
    }



}
