package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.UserDTO;
import com.cgtravelokaservice.dto.request.LoginRequest;
import com.cgtravelokaservice.entity.user.CustomOAuth2User;
import com.cgtravelokaservice.jwt.service.JwtResponse;
import com.cgtravelokaservice.jwt.service.JwtService;
import com.cgtravelokaservice.service.IUserService;
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

import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager
            authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @PostMapping("/account")
    public ResponseEntity <?> login(@Validated @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body("Your request is not valid. Check again your username or password");
        }
        try {
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt =
                    jwtService.generateTokenLogin(authentication);
            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();
            Optional <UserDTO> userInfoOp =
                    userService.findValidUserDTOByAccountName(loginRequest.getUsername());
            if (userInfoOp.isPresent()) {
                UserDTO userInfo =
                        userInfoOp.get();
                if (userInfo.isActive()) {
                    return ResponseEntity.ok(new JwtResponse(jwt, userInfo.getUsername(), userInfo.getUsername(), userDetails.getAuthorities()));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User is not active. Please fill " + "full " + "register steps to login");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User is not active. Please fill full " + "register steps to login");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fail");
        }
    }

    @GetMapping("/o2auth/success")
    public ResponseEntity <?> o2auth() {
        try {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            CustomOAuth2User userInfo =
                    (CustomOAuth2User) authentication.getPrincipal();
            String jwt =
                    jwtService.generateTokenLogin(authentication);
            return ResponseEntity.ok(new JwtResponse(jwt, userInfo.getEmail(), userInfo.getName(), userInfo.getAuthorities()));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login with oauth fail");
        }
    }


}
