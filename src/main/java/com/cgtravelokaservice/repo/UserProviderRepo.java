package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.user.UserProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProviderRepo extends JpaRepository<UserProvider, Long> {
}
