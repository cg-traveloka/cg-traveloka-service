package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {
}
