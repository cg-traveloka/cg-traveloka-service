package com.codegym.repository;

import com.codegym.model.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProviderRepo extends JpaRepository<Provider, Integer> {
    Optional<Provider> findByName(String name);
}
