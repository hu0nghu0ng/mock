package com.example.newmockup.controller;


import com.example.newmockup.exception.FileResponseException;
import com.example.newmockup.payload.FileResponse;
import com.example.newmockup.service.MinioService;
import com.example.newmockup.utils.FileTypeUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/minio")
@Slf4j
public class MinioController {

    @Value("${server.port}")
    private int portNumber;

    private final MinioService minioService;

    @GetMapping("/exist/{bucketName}")
    public ResponseEntity<Object> check(@PathVariable("bucketName") String bucketName) {
        return new ResponseEntity<>(minioService.bucketExists(bucketName), HttpStatus.OK);
    }

    @PostMapping("/addBucket/{bucketName}")
    public String addBucket(@PathVariable("bucketName") String bucketName) {
        minioService.makeBucket(bucketName);
        return "Bucket " + bucketName + "is created";
    }

    @GetMapping("/showBucketName")
    public List<String> showBucketName() {
        return minioService.listBucketName();
    }

    @PostMapping("/upload")
    public FileResponse uploadFile(MultipartFile file, String bucketName) {
        String fileType = FileTypeUtils.getFileType(file);
        if (fileType != null) {
            return minioService.putObject(file, bucketName, fileType);
        }
        throw new FileResponseException("File cannot be Upload");
    }

    @GetMapping("/show/{bucketName}")
    public List<String> listObjectName(@PathVariable("bucketName") String bucketName) {
        return minioService.listObjectNames(bucketName);
    }

    @GetMapping("/download/{bucketName}/{objectName}")
    public void download(HttpServletResponse response, @PathVariable("bucketName") String bucketName, @PathVariable("objectName") String objectName) {
        InputStream in = null;
        try {

            in = minioService.downloadObject(bucketName, objectName);
            response.setHeader("Content-Disposition", "attachment;filename="
             + URLEncoder.encode(objectName, "UTF-8"));
            response.setCharacterEncoding("UTF-8");

            // Remove bytes from InputStream Copied to the OutputStream
            IOUtils.copy(in, response.getOutputStream());

        } catch (UnsupportedEncodingException e) {
            log.info("MinioController | download | UnsupportedEncodingException : " + e.getMessage());
        } catch (IOException e) {
            log.info("MinioController | download | IOException : " + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.info("MinioController | download | IOException : " + e.getMessage());
                }
            }
        }
    }

    @GetMapping("/showListObjectNameAndDownloadUrl/{bucketName}")
    public Map<String, String> showListObjectNameAndDownloadURL(@PathVariable("bucketName") String bucketName) {
        Map<String, String> map = new HashMap<>();
        List<String> listObjectName = minioService.listObjectNames(bucketName);

        String url = "localhost:" + portNumber + "/minio/download/" + bucketName + "/";

        for (int i = 0; i < listObjectName.size(); i++) {
            map.put(listObjectName.get(i), url + listObjectName.get(i));
        }

        return map;
    }

    @DeleteMapping("/removeBucket/{bucketName}")
    public String deleteBucket(@PathVariable("bucketName") String bucketName) {
        boolean state = minioService.removeBucket(bucketName);
        if (state) {
            return "Remove bucket succesfully";
        }
        return "Remove bucket failed";
    }

    @DeleteMapping("/removeObject/{bucketName}/{objectName}")
    public String deleteObject(@PathVariable("bucketName") String bucketName, @PathVariable("objectName") String objectName) {
        boolean state = minioService.removeObject(bucketName, objectName);
        if (state) {
            return "Remove object succesfully";
        }
        return "Remove object failed";
    }






}
