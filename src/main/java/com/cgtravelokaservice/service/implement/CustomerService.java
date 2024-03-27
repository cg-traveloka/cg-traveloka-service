package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.dto.request.UpdateProfileCustomerRequestDTO;
import com.cgtravelokaservice.entity.user.Customer;
import com.cgtravelokaservice.repo.CustomerRepo;
import com.cgtravelokaservice.service.ICustomerService;
import com.cgtravelokaservice.util.implement.ConvertUtil;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService {
    private final CustomerRepo customerRepo;

    private final ConvertUtil convertUtil;

    public CustomerService(CustomerRepo customerRepo, ConvertUtil convertUtil) {
        this.customerRepo = customerRepo;
        this.convertUtil = convertUtil;
    }

    @Override
    public boolean updateCustomer(UpdateProfileCustomerRequestDTO requestDTO) {
        try {
            Customer customer = convertUtil.convertDTOToCustomer(requestDTO);
            customerRepo.save(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Customer getCustomerProfile(Integer id) {
        return customerRepo.getReferenceById(id);
    }
}
