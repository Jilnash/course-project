package com.jilnash.courseproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.ByteBuffer;
import java.util.Objects;

@Service
public class S3Service {

    @Autowired
    private S3Client s3;

    public void putFileToS3(MultipartFile file, String bucketName) throws Exception {

        CreateBucketRequest createBucketRequest = CreateBucketRequest.builder()
                    .bucket(bucketName)
                    .build();

        s3.createBucket(createBucketRequest);

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(String.format("audio.%s", Objects.requireNonNull(file.getContentType()).split("/")[1]))
                .build();

         s3.putObject(
                putObjectRequest, 
                software.amazon.awssdk.core.sync.RequestBody.fromByteBuffer(ByteBuffer.wrap(file.getBytes()))
        );

    }

    public Resource getFileFromS3(String bucketName, String fileName) {

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        ResponseInputStream<GetObjectResponse> object = s3.getObject(getObjectRequest);

        return new InputStreamResource(object);
    }
}
