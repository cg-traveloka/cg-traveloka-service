package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepo extends JpaRepository <Customer, Integer> {
    Customer findByUserUsername(String userName);
}
