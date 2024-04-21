package com.lig.ConferenceSessionTrackAPI.service.Impl;

import com.lig.ConferenceSessionTrackAPI.model.Talk;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension .class)
class ConferenceScheduleServiceImplTest {

    @Mock
    private MultipartFile file;

    private ConferenceScheduleServiceImpl conferenceScheduleService;

    @BeforeEach
    void setUp() {
        conferenceScheduleService = new ConferenceScheduleServiceImpl();
    }


    @Test
    void processExcelFileReturnsExpectedResponse() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Test");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Talk 1");
        row.createCell(1).setCellValue(60);

        // Convertir el libro de trabajo a un array de bytes
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } finally {
            bos.close();
        }
        byte[] bytes = bos.toByteArray();

        // Crear un MultipartFile a partir del array de bytes
        MultipartFile file = new MockMultipartFile("file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", bytes);

        // Probar el método processExcelFile()
        ResponseEntity<?> response = conferenceScheduleService.processExcelFile(file);
        assertEquals(200, response.getStatusCodeValue());
    }


}