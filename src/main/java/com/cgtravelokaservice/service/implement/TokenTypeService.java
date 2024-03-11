package com.codegym.service.impl;

import com.codegym.model.entity.TokenType;
import com.codegym.repository.TokenTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TokenTypeService {
    @Autowired
    private TokenTypeRepo tokenTypeRepo;

    public Optional<TokenType> findByName(String name) {
        return tokenTypeRepo.findByName(name.toUpperCase());
    }
}
