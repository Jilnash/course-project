package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.request.education.HomeworkDTO;
import com.jilnash.courseproject.dto.request.education.HwResponseDTO;
import com.jilnash.courseproject.dto.response.AppResponse;
import com.jilnash.courseproject.service.HomeworkService;
import com.jilnash.courseproject.service.HwResponseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@RestController
@RequestMapping("homeworks")
@CrossOrigin(origins = "*")
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private HwResponseService hwResponseService;

    @GetMapping
    public ResponseEntity<?> getHomeworks(
            @RequestParam(value = "studentId", required = false)    Long studentId,
            @RequestParam(value = "taskId", required = false)       Long taskId,
            @RequestParam(value = "checked", required = false)      Boolean checked,
            @RequestParam(value = "createdAfter", required = false) Date createdAfter) {

        return ResponseEntity.ok(
                new AppResponse(
                        "List of homeworks",
                        200,
                        homeworkService.getHomeworks(studentId, taskId, checked, createdAfter)
                )
        );
    }

    @PutMapping
    public ResponseEntity<?> createHomework(@RequestParam("taskId") Long taskId,
                                            @RequestParam("audio") MultipartFile audio,
                                            @RequestParam("video") MultipartFile video) throws Exception {
        @Valid
        HomeworkDTO homeworkDTO = new HomeworkDTO(taskId, audio, video);

        String studentLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        return ResponseEntity.ok(
                new AppResponse(
                        "Homework created successfully",
                        200,
                        homeworkService.createHomework(homeworkDTO, studentLogin)
                )
        );
    }

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<?> getHomework(@PathVariable Long id) throws Exception {

        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"audio.mpeg\"")
                .body(homeworkService.getHomeworkAudio(id).getContentAsByteArray());
    }

    @PutMapping("{id}/response")
    public ResponseEntity<?> createResponseToHomework(@PathVariable Long id,
                                                      @Valid @RequestBody HwResponseDTO responseDTO) {

        String teacherLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();

        return ResponseEntity.ok(
                new AppResponse(
                        "Response created successfully",
                        200,
                        hwResponseService.createResponseToHomework(id, responseDTO, teacherLogin)
                )
        );
    }

    @GetMapping("{hwId}/response")
    public ResponseEntity<?> getResponseOfHomework(@PathVariable Long hwId) {

        return ResponseEntity.ok(
                new AppResponse(
                        "Response of homework",
                        200,
                        hwResponseService.getResponse(hwId)
                )
        );
    }
}
