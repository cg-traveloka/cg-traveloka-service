package com.cgtravelokaservice.service;

import com.cgtravelokaservice.entity.token.Token;
import com.cgtravelokaservice.entity.user.User;

public interface ITokenService {

    boolean isCodeValid(String email, String code);
    Token generateOrRefreshCode(User user);
    boolean add(Token token);

}
