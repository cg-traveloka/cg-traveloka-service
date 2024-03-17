package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.token.Token;
import com.cgtravelokaservice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {
    List<Token> getByUser_EmailAndType_Name(String email, String tokenType);

    Optional<Token> findByCode(String code);
   Optional<Token> findByUser(User user);
}
