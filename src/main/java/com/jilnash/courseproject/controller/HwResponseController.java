package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.model.education.HwResponse;
import com.jilnash.courseproject.service.ResponseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response")
public class HwResponseController {


    @GetMapping
    public HwResponse getResponse() {

        return ResponseService.getHwResponse();
    }

    @PostMapping
    public void createResponse() {

    }
}
