package com.cgtravelokaservice.service;

import com.cgtravelokaservice.entity.token.TokenType;

import java.util.Optional;

public interface ITokenTypeService {
    Optional<TokenType> findByName(String name);
}
