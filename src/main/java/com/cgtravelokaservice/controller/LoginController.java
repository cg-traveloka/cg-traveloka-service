package com.codegym.controller;

import com.codegym.jwt.service.JwtResponse;
import com.codegym.jwt.service.JwtService;
import com.codegym.model.CustomOAuth2User;
import com.codegym.model.dto.UserDTO;
import com.codegym.model.entity.User;
import com.codegym.model.request.LoginRequest;
import com.codegym.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @PostMapping("/account")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body("Your request is not valid. Check again your username or password");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDTO userInfo = userService.findValidUserDTOByAccountName(loginRequest.getUsername()).get();
            if (userInfo.isActive()) {
                return ResponseEntity.ok(new JwtResponse(jwt,
                        userInfo.getUsername(), userInfo.getUsername(), userDetails.getAuthorities()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User is not active. Please fill full " +
                        "register steps to login");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/o2auth/success")
    public ResponseEntity<?> o2auth() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomOAuth2User userInfo = (CustomOAuth2User) authentication.getPrincipal();
            String jwt = jwtService.generateTokenLogin(authentication);
            return ResponseEntity.ok(new JwtResponse(jwt, userInfo.getEmail(), userInfo.getName(),
                    userInfo.getAuthorities()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }



}
