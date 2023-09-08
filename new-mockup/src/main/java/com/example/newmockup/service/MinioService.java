package com.example.newmockup.service;

import com.example.newmockup.payload.FileResponse;
import io.minio.messages.Bucket;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface MinioService {
    boolean bucketExists(String bucketName);

    void makeBucket(String bucketName);

    List<String> listBucketName();

    List<Bucket> listBuckets();

    // delete bucket by name
    boolean removeBucket(String bucketName);

    // upload file in the bucket
    FileResponse putObject(MultipartFile multipartFile, String bucketName, String fileType);

    // list all object names in the bucket
    List<String> listObjectNames(String bucketName);

    // Download file from bucket
    InputStream downloadObject(String bucketName, String objectName);

    // delete file in bucket
    boolean removeObject(String bucketName, String objectName);

    // delete files in bucket
    boolean removeListObject(String bucketName, List<String> objectNameList);

    // get file path from bucket
    String getObjectURL(String bucketName, String objectName);

}
