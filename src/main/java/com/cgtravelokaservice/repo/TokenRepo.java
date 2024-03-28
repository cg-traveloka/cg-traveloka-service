package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.token.Token;
import com.cgtravelokaservice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {
    Optional<Token> findByUser(User user);
    Optional<Token> findByCode(String code);
}
