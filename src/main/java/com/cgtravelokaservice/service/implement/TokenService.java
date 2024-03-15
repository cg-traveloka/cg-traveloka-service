package com.cgtravelokaservice.service.implement;


import com.cgtravelokaservice.entity.token.Token;
import com.cgtravelokaservice.repo.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TokenService implements com.cgtravelokaservice.service.ITokenService {
    @Autowired
    private TokenRepo tokenRepo;


    @Override
    public List<Token> getTokenByUserIdAndTokenType(String email, String tokenType) {
        return tokenRepo.getByUser_EmailAndType_Name(email, tokenType);
    }

    @Override
    public boolean isTokenValid(String email, String code) {
        Optional<Token> tokenOptional = tokenRepo.findByCode(code);
        if (tokenOptional.isPresent()) {
            Token token = tokenOptional.get();
            return token.getUser().getEmail().equals(email) && token.getExpiredTime().getTime() > System.currentTimeMillis() && token.isStatus();
        }
        return false;
    }

    @Override
    public boolean add(Token token) {
        try {
            tokenRepo.save(token);
            return true;
        } catch (Exception e) {
            System.out.println("Can not add token" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean disableTokenByType(String email, String type) {
        List<Token> tokens = getTokenByUserIdAndTokenType(email, type);
        if (!tokens.isEmpty()) {
            for (Token token : tokens) {
                token.setStatus(false);
            }
        }
        return true;
    }

    @Override
    @Scheduled(fixedRate = 5000)
    public void clearToken() {
        tokenRepo.findAll().forEach(token -> {
            if (token.getExpiredTime().getTime() <= System.currentTimeMillis()) {
                token.setStatus(false);
            }
        });
    }
}
