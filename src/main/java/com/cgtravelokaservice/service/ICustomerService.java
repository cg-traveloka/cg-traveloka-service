package com.cgtravelokaservice.service;

import com.cgtravelokaservice.dto.request.UpdateProfileCustomerRequestDTO;
import com.cgtravelokaservice.entity.user.Customer;

public interface ICustomerService {
    boolean updateCustomer(UpdateProfileCustomerRequestDTO requestDTO);

    Customer getCustomerProfile(Integer id);
}
