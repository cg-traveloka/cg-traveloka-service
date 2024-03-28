package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.user.Customer;
import com.cgtravelokaservice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository <Customer, Integer> {
    Customer findByUserUsername(String userName);

    Customer findByUser(User user);
}
