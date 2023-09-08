package com.example.newmockup.service;


import com.example.newmockup.config.MinioConfig;
import com.example.newmockup.payload.FileResponse;
import com.example.newmockup.utils.MinioUtil;
import io.minio.messages.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MinioServiceImpl implements MinioService {

    private final MinioConfig minioConfig;
    private final MinioUtil minioUtil;


    @Override
    public boolean bucketExists(String bucketName) {
        log.info("MinioServiceImpl | bucketExists is called");
        return minioUtil.bucketExists(bucketName);
    }

    @Override
    public void makeBucket(String bucketName) {
        minioUtil.makeBucket(bucketName);
    }

    @Override
    public List<String> listBucketName() {
        return minioUtil.listBucketName();
    }

    @Override
    public List<Bucket> listBuckets() {
        return minioUtil.listBuckets();
    }

    @Override
    public boolean removeBucket(String bucketName) {
        return minioUtil.removeBucket(bucketName);
    }

    @Override
    public FileResponse putObject(MultipartFile multipartFile, String bucketName, String fileType) {

        try {

            bucketName = StringUtils.isNotBlank(bucketName) ? bucketName : minioConfig.getBucketName();

            if (!this.bucketExists(bucketName)) {
                this.makeBucket(bucketName);
            }

            String fileName = multipartFile.getOriginalFilename();

            Long fileSize = multipartFile.getSize();

            String objectName = UUID.randomUUID().toString().replaceAll("-", "")
                    + fileName.substring(fileName.lastIndexOf("-"));

            LocalDateTime createdTime = LocalDateTime.now();

            minioUtil.putObject(bucketName, multipartFile, objectName, fileType);

            return FileResponse.builder()
                    .filename(objectName)
                    .fileSize(fileSize)
                    .contentType(fileType)
                    .createdTime(createdTime)
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<String> listObjectNames(String bucketName) {
        return minioUtil.listObjectName(bucketName);
    }

    @Override
    public InputStream downloadObject(String bucketName, String objectName) {
        return minioUtil.getObject(bucketName, objectName);
    }

    @Override
    public boolean removeObject(String bucketName, String objectName) {
        return minioUtil.removeObject(bucketName, objectName);
    }

    @Override
    public boolean removeListObject(String bucketName, List<String> objectNameList) {
        return false;
    }

    @Override
    public String getObjectURL(String bucketName, String objectName) {
        return minioUtil.getObjectUrl(bucketName, objectName);
    }
}
