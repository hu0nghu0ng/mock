package com.example.newmockup.utils;


import com.example.newmockup.config.MinioConfig;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class MinioUtil {
    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        log.info("MinioUtil | bucketExists is called");

        boolean found = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build()
        );

        log.info("MinioUtil | bucketExists | found : " + found);

        if (found) {
            System.err.println("found");
            log.info("MinioUtil | bucketExists | message : " + bucketName + " exists");
        } else {
            log.info("MinioUtil | bucketExists | message : " + bucketName + " does not exist");
        }
        return found;
    }

    // create bucket name
    @SneakyThrows
    public boolean makeBucket(String bucketName) {
        boolean flag = bucketExists(bucketName);
        if (!flag) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
            return true;
        }
        return false;
    }

    // list all buckets
    @SneakyThrows
    public List<Bucket> listBuckets() {
        return minioClient.listBuckets();
    }

    // list all bucket name
    public List<String> listBucketName() {
        List<Bucket> bucketList = listBuckets();
        List<String> listBucketname = new ArrayList<>();
        for (Bucket b : bucketList) {
            listBucketname.add(b.name());
        }
        return listBucketname;
    }

    // upload file
    @SneakyThrows
    public void putObject(String bucketName, MultipartFile multipartFile, String filename, String fileType) {
        InputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes());

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(filename)
                        .stream(inputStream, -1, minioConfig.getFileSize())
                        .contentType(fileType)
                        .build()
        );


    }

    // list all objects from the specified bucket
    @SneakyThrows
    public Iterable<Result<Item>> listObjects(String bucketName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            return minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
        }
        return null;
    }

    // list all objects name in bucket
    @SneakyThrows
    public List<String> listObjectName(String bucketName) {
        List<String> listObjectName = new ArrayList<>();
        boolean flag = bucketExists(bucketName);
        if (flag) {
            Iterable<Result<Item>> myObjects = listObjects(bucketName);
            for (Result<Item> r : myObjects) {
                Item item = r.get();
                listObjectName.add(item.objectName());
            }
        } else {
            listObjectName.add("Bucket does not exists");
        }
        return listObjectName;
    }

    // get metadata of the object from specified bucket
    @SneakyThrows
    public StatObjectResponse statObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            StatObjectResponse stat = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return stat;
        }
        return null;
    }

    @SneakyThrows
    // get a file object as a stream from the specified bucket
    public InputStream getObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            StatObjectResponse statObjectResponse = statObject(bucketName, objectName);
            if (statObjectResponse != null && statObjectResponse.size() > 0) {
                InputStream stream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .build()
                );
                return stream;
            }
        }
        return null;
    }

    // get a file object as a stream from the specified bucket (breakpoint download)
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName, long offset, Long length) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            StatObjectResponse statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.size() > 0) {
                InputStream inputStream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(bucketName)
                                .object(objectName)
                                .object(objectName)
                                .length(length)
                                .build()
                );
                return inputStream;
            }
        }
        return null;
    }

    // get file path from the specified bucket
    @SneakyThrows
    public String getObjectUrl(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        String url = "";
        if (flag) {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(2, TimeUnit.MINUTES)
                            .build()
            );
        }
        return url;
    }

    // delete bucket by its name from specified bucket
    @SneakyThrows
    public boolean removeBucket(String bucketName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            Iterable<Result<Item>> myObjects = listObjects(bucketName);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                // Delete failed when there are object files in bucket
                if (item.size() > 0) {
                    return false;
                }
            }

            // Delete bucket when bucket is empty
            minioClient.removeBucket(
                    RemoveBucketArgs.builder()
                            .bucket(bucketName)
                            .build()
            );
            flag = bucketExists(bucketName);
            if (!flag) {
                return true;
            }
        }
        return false;
    }

    @SneakyThrows
    public boolean removeObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
            return true;
        }
        return false;
    }

}
