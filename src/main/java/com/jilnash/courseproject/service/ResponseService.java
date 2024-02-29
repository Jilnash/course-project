package com.jilnash.courseproject.service;

import com.jilnash.courseproject.model.education.HwResponse;
import com.jilnash.courseproject.repo.ResponseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepo responseRepo;

    public static HwResponse getHwResponse() {

        return null;
    }

    public static void createHwResponse() {

    }
}
