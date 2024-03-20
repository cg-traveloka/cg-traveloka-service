package com.cgtravelokaservice.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        Map <String, String> errorResponse =
                new HashMap <>();
        errorResponse.put("error", "OAuth2 authentication failed");
        errorResponse.put("message", exception.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}