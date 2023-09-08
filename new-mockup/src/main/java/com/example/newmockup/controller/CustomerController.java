package com.example.newmockup.controller;

import com.example.newmockup.service.CustomerService;
import com.example.newmockup.utils.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/import")
    public ResponseEntity<BaseResponse> importCustomerData(@RequestParam("file") MultipartFile importFile) {
        System.err.println("heheheheheheheh");
        BaseResponse baseResponse = customerService.importCustomerData(importFile);
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
