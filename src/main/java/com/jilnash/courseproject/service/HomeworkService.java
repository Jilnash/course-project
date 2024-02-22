package com.jilnash.courseproject.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class HomeworkService {

    public static void createHomework(MultipartFile audio, MultipartFile video) throws IOException {

        YanexDiskService.putFileToDisk(audio);
        YanexDiskService.putFileToDisk(video);
    }

    public static String getStudentHomework(String path) {

        return YanexDiskService.getFileFromDisk(path);
    }
}
