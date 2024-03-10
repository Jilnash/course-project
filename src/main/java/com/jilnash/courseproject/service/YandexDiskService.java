package com.jilnash.courseproject.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class YandexDiskService {

    private static final String authToken = "y0_AgAAAAAkLYXfAAsTZwAAAAD28-CO6IKwXK0JQc63rvJHvmJ90HZjHBA";
    private static final String uploadURL = "https://cloud-api.yandex.net/v1/disk/resources/upload?path=%s&overwrite=true";
    private static final String downloadURL = "https://cloud-api.yandex.net/v1/disk/resources/download?path=%s";

    public static void putFileToDisk(MultipartFile file, String path) {

        String link = getUploadRequestLink(path);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.exchange(
                link,
                HttpMethod.PUT,
                new HttpEntity<>(file.getResource()),
                String.class
        );
    }

    private static String getUploadRequestLink(String path) {

        String requestUrl = String.format(uploadURL, path);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", authToken);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        return convertBodyToMap(response.getBody()).get("href");
    }

    public static String getFileFromDisk(String path) {
        String link = getDownloadRequestLink(path);

        return link;
//        RestTemplate restTemplate = new RestTemplate();
//
//        try {
//            ResponseEntity<Resource> response = restTemplate.exchange(
//                    link,
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<Resource>() {
//                    }
//            );
//            return response.getBody();
//
//        } catch (Exception e) {
//            throw new UsernameNotFoundException("File request failed");
//        }
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
