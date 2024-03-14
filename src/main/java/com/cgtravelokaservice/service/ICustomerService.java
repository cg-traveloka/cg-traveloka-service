package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.request.UpdateProfileCustomerRequestDTO;

public interface ICustomerService {
    boolean updateCustomer(UpdateProfileCustomerRequestDTO requestDTO);
}
