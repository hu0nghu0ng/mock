package com.example.newmockup.service;

import com.example.newmockup.utils.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {
    BaseResponse importCustomerData(MultipartFile importFile);
}
