package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.request.UpdateProfileCustomerRequestDTO;
import com.cgtravelokaservice.service.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/api/customers/update")
    public ResponseEntity<?> updateProfileCustomer(@RequestBody UpdateProfileCustomerRequestDTO requestDTO) {
        if (customerService.updateCustomer(requestDTO)) {
            return ResponseEntity.ok("Cập nhật thông tin khách hàng thành công");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật thông tin khách hàng thất bại");
        }
    }
}
