package com.cgtravelokaservice.service;

import com.cgtravelokaservice.entity.token.Token;

import java.util.List;

public interface ITokenService {
    List<Token> getTokenByUserIdAndTokenType(String email, String tokenType);

    boolean isTokenValid(String email, String code);

    boolean add(Token token);

    boolean disableTokenByType(String email, String type);

    void clearToken();
}
