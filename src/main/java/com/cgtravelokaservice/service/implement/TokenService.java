package com.cgtravelokaservice.service.implement;


import com.cgtravelokaservice.entity.token.Token;
import com.cgtravelokaservice.entity.user.User;
import com.cgtravelokaservice.repo.TokenRepo;
import com.cgtravelokaservice.service.ITokenService;
import com.cgtravelokaservice.util.RandomDigitsGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class TokenService implements ITokenService {
    @Autowired
    private TokenRepo tokenRepo;


    public Optional<Token> findByUser(User user) {
        return tokenRepo.findByUser(user);
    }

    public Token generateOrRefreshCode(User user) {
        Optional<Token> tokenOptional = findByUser(user);
        Token token;

        if (tokenOptional.isPresent()) {
            token = tokenOptional.get();
            String newCode = RandomDigitsGenerator.generate(6);
            token.setCode(newCode);
            token.setCodeExpiredAt(new Timestamp(System.currentTimeMillis() + 15 * 60 * 1000));
        } else {
            String code = RandomDigitsGenerator.generate(6);
            token = Token.builder()
                    .code(code)
                    .user(user)
                    .codeExpiredAt(new Timestamp(System.currentTimeMillis() + 15 * 60 * 1000))
                    .build();
        }
        return tokenRepo.save(token);
    }

    public boolean isCodeValid(String email, String code) {
        Optional<Token> tokenOptional = tokenRepo.findByCode(code);
        if (tokenOptional.isPresent()) {
            Token token = tokenOptional.get();

                return token.getCode().equals(code) && token.getCodeExpiredAt().after(new Timestamp(System.currentTimeMillis()));

        }
        return false;
    }

    @Override
    public boolean add(Token token) {
        try {
            Optional<Token> tokenOptional = tokenRepo.findByUser(token.getUser());
            if (tokenOptional.isPresent() & !isCodeValid(token.getUser().getEmail(), token.getCode())) {
                Token token1 = tokenOptional.get();
                token1.setCode(token.getCode());
                tokenRepo.save(token1);
            } else {
                tokenRepo.save(token);
            }
            return true;
        } catch (Exception e) {
            System.out.println("Không thể thêm token: " + e.getMessage());
            return false;
        }
    }


}
