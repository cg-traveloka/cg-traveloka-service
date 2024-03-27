package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.request.UpdateProfileCustomerRequestDTO;
import com.cgtravelokaservice.entity.user.Customer;
import com.cgtravelokaservice.service.ICustomerService;
import com.cgtravelokaservice.util.implement.ConvertUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private final ICustomerService
            customerService;

    private final ConvertUtil convertUtil;

    public CustomerController(ICustomerService customerService, ConvertUtil convertUtil) {
        this.customerService = customerService;
        this.convertUtil = convertUtil;
    }

    @PutMapping("/api/customers/{id}")
    public ResponseEntity<?> updateProfileCustomer(@RequestBody UpdateProfileCustomerRequestDTO requestDTO,
                                                   @PathVariable Integer id) {
        if (customerService.updateCustomer(requestDTO)) {
            return ResponseEntity.ok("Cập nhật thông tin khách hàng thành công");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật thông tin khách hàng thất bại");
        }
    }

    @GetMapping("/api/customers/{id}")
    public ResponseEntity<?> getCustomerProfile(@PathVariable Integer id) {
        Customer customer = customerService.getCustomerProfile(id);
        return ResponseEntity.ok(convertUtil.convertToResponseDTO(customer));
    }
}
