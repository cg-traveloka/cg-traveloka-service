package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.request.UpdateProfileCustomerRequestDTO;
import com.cgtravelokaservice.entity.user.Customer;
import com.cgtravelokaservice.repo.CustomerRepo;
import com.cgtravelokaservice.repo.UserRepo;
import com.cgtravelokaservice.service.ICustomerService;
import com.cgtravelokaservice.util.implement.ConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private final ICustomerService customerService;

    private final ConvertUtil convertUtil;


    private final CustomerRepo customerRepo;

    private final UserRepo userRepo;

    public CustomerController(ICustomerService customerService, ConvertUtil convertUtil, CustomerRepo customerRepo, UserRepo userRepo) {
        this.customerService = customerService;
        this.convertUtil = convertUtil;
        this.customerRepo = customerRepo;
        this.userRepo = userRepo;
    }


    @PutMapping("/api/customers")
    public ResponseEntity<?> updateProfileCustomer(@RequestBody UpdateProfileCustomerRequestDTO requestDTO) {

        if (customerService.updateCustomer(requestDTO)) {
            return ResponseEntity.ok("Cập nhật thông tin khách hàng thành công");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cập nhật thông tin khách hàng thất bại");
        }
    }

    @GetMapping("/api/customers")
    public ResponseEntity<?> getCustomerProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Customer customer = customerRepo.findByUser(userRepo.findByUsername(username).orElse(null));
        return ResponseEntity.ok(convertUtil.convertToResponseDTO(customer));
    }
}
