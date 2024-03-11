package com.cgtravelokaservice.service.implement;


import com.cgtravelokaservice.entity.token.TokenType;
import com.cgtravelokaservice.repo.TokenTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TokenTypeService implements com.cgtravelokaservice.service.ITokenTypeService {
    @Autowired
    private TokenTypeRepo tokenTypeRepo;

    @Override
    public Optional<TokenType> findByName(String name) {
        return tokenTypeRepo.findByName(name.toUpperCase());
    }
}
