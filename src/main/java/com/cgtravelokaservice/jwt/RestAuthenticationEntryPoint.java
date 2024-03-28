package com.cgtravelokaservice.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Map <String, Object> data =
                new HashMap <>();
        ZonedDateTime now =
                ZonedDateTime.now(ZoneOffset.UTC);
        DateTimeFormatter formatter =
                DateTimeFormatter.ISO_INSTANT;
        data.put("timestamp", now.format(formatter));
        data.put("status", HttpServletResponse.SC_FORBIDDEN);
        data.put("error", "Forbidden");
        data.put("message", "Access Denied");
        data.put("path", request.getRequestURI());
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getOutputStream().println(new ObjectMapper().writeValueAsString(data));
    }
}