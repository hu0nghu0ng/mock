package com.example.newmockup.service;

import com.example.newmockup.model.Customer;
import com.example.newmockup.repository.CustomerRepository;
import com.example.newmockup.utils.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public BaseResponse importCustomerData(MultipartFile importFile) {

        BaseResponse baseResponse = new BaseResponse();

        Workbook workbook = FileFactory.getWorkbookStream(importFile);

        List<CustomerDTO> customerDTOList = ExcelUtils.getImportDATA(workbook, ImportConfig.customerImport);

        if(!CollectionUtils.isEmpty(customerDTOList)){
            saveData(customerDTOList);
            baseResponse.setCode(String.valueOf(HttpStatus.OK));
            baseResponse.setMessage("Import successfully");
        }else{
            baseResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
            baseResponse.setMessage("Import failed");
        }

        return baseResponse;
    }
    private void saveData(List<CustomerDTO> customerDTOList){
        for(CustomerDTO customerDTO : customerDTOList){
            Customer customer = new Customer();
            customer.setCustomerNumber(customerDTO.getCustomerNumber());
            customer.setCustomerName(customerDTO.getCustomerName());
            customerRepository.save(customer);
        }
    }

}
