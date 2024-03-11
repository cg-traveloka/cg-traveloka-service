package com.cgtravelokaservice.jwt.service;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

@Data

public class JwtResponse {
    private Long id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String name;
    private final Collection<? extends GrantedAuthority> authorities;
    private static final AtomicLong counter = new AtomicLong();


    public JwtResponse( String token, String username, String name, Collection<? extends GrantedAuthority> authorities) {
        id = counter.incrementAndGet();
        this.token = token;
        this.username = username;
        this.name = name;
        this.authorities = authorities;
    }


}