package com.jilnash.courseproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class YanexDiskService {

    @Value("${yandex.disk.auth.token}")
    private static String authToken;
    private static String uploadURL = "https://cloud-api.yandex.net/v1/disk/resources/upload?path=%s&overwrite=true";
    private static String downloadURL = "https://cloud-api.yandex.net/v1/disk/resources/download?path=%s";

    public static void putFileToDisk(MultipartFile file) throws IOException {

        String link = getUploadRequestLink(file);

        RestTemplate restTemplate = new RestTemplate();

        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        };

        HttpEntity<ByteArrayResource> requestEntity = new HttpEntity<>(resource);

        restTemplate.exchange(link, HttpMethod.PUT, requestEntity, String.class);
    }

    private static String getUploadRequestLink(MultipartFile multipartFile) {

        String requestUrl = String.format(uploadURL, multipartFile.getOriginalFilename());

        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", authToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                entity,
                String.class
        );

        return convertBodyToMap(response.getBody()).get("href");
    }

    public static String getFileFromDisk(String path) {

        String link = getDownloadRequestLink(path);

//        RestTemplate restTemplate = new RestTemplate();
//
//        restTemplate.getForEntity(link, String.class) ;

        return link;
    }

    private static String getDownloadRequestLink(String path) {

        String requestUrl = String.format(downloadURL, path);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", authToken);

        RestTemplate restTemplate = new RestTemplateBuilder().build();

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        return convertBodyToMap(response.getBody()).get("href");
    }

    private static Map<String, String> convertBodyToMap(String body) {

        Gson gson = new Gson();

        // Parse JSON string to Map<String, String>
        return gson.fromJson(body, new TypeToken<Map<String, String>>() {}.getType());
    }
}
