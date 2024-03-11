package com.cgtravelokaservice.service.implement;


import com.cgtravelokaservice.entity.user.Role;
import com.cgtravelokaservice.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements com.cgtravelokaservice.service.IRoleService {

    @Autowired
    private RoleRepo roleRepo;
    @Override
    public Optional<Role> findByName(String name) {
        return roleRepo.findByName(name);
    }
}
