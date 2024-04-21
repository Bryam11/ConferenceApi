package com.lig.ConferenceSessionTrackAPI.controller;

import com.lig.ConferenceSessionTrackAPI.service.ConferenceScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Description("File Upload Controller")
@Tag(name = "file-upload")
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileUploadController {

    private final ConferenceScheduleService conferenceScheduleService;


    @PostMapping(value = "/upload" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @Operation(summary = "Upload Excel File", description = "Upload Excel File to process")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Not found", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))})
    })

    public ResponseEntity<?> downloadFile(@RequestPart("file") MultipartFile file) {
        return  conferenceScheduleService.processExcelFile(file);
    }

}
