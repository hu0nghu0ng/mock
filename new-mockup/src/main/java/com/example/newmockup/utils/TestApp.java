package com.example.newmockup.utils;

import com.example.newmockup.config.MinioConfig;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;

import java.util.List;

public class TestApp {


    public static void main(String[] args) {

        MinioClient minioClient = MinioClient.builder()
                .credentials("minioadmin", "minioadmin")
                .endpoint("http://127.0.0.1:9000/", 9000, false)
                .build();
        try {

            List<Bucket> bList = minioClient.listBuckets();
            System.err.println("Connection success, total bucket: " + bList.size());

        } catch (MinioException e) {
            System.err.println("Connection failed: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
