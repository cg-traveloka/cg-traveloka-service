package com.cgtravelokaservice.service;

import com.cgtravelokaservice.entity.user.Role;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(String name);

}
