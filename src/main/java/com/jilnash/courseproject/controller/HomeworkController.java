package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.service.HomeworkService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("homework")
@CrossOrigin(origins = "http://localhost:5173")
public class HomeworkController {

    @GetMapping
    public String getStudentHW(@RequestParam String path) {

        return HomeworkService.getStudentHomework(path);
    }

    @PostMapping
    public void postHW(@ModelAttribute("video") MultipartFile video,
                       @ModelAttribute("audio") MultipartFile audio) throws IOException {

        HomeworkService.createHomework(audio, video);
    }
}
