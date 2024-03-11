package com.codegym.service.impl;

import com.codegym.model.entity.Role;
import com.codegym.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;

    public Optional<Role> findByName(String name) {
        return roleRepo.findByName(name);
    }
}
