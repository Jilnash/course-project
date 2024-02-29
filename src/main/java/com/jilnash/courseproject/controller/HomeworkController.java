package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.request.education.HwResponseDTO;
import com.jilnash.courseproject.service.HomeworkService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("homeworks")
@CrossOrigin(origins = "http://localhost:5173")
public class HomeworkController {

    @GetMapping
    public String getHomeworks(@RequestParam String path) {

        return HomeworkService.getStudentHomework(path);
    }

    @PutMapping
    public void createHomework(@ModelAttribute("video") MultipartFile video,
                               @ModelAttribute("audio") MultipartFile audio) throws IOException {

        HomeworkService.createHomework(audio, video);
    }

    @GetMapping("{id}")
    public String getHomework(@PathVariable Long id) {

        return "Homework " + id;
    }

    @PutMapping("{id}/responses")
    public String createResponseToHomework(@PathVariable Long id,
                                           @Valid @RequestBody HwResponseDTO responseDTO) {

        System.out.println(responseDTO);

        return "Created response to homework " + id;
    }

    @GetMapping("{id}/responses/{responseId}")
    public String getResponseOfHomework(@PathVariable Long id,
                                        @PathVariable Long responseId) {

        return "Response " + responseId +" of homework " + id;
    }
}
