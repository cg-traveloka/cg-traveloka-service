package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.token.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TokenTypeRepo extends JpaRepository<TokenType, Integer> {

    Optional<TokenType> findByName(String name);
}
