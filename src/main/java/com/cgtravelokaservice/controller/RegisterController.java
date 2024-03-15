package com.cgtravelokaservice.controller;


import com.cgtravelokaservice.dto.request.ValidateCodeRequest;
import com.cgtravelokaservice.entity.token.Token;
import com.cgtravelokaservice.entity.user.User;
import com.cgtravelokaservice.repo.UserRepo;
import com.cgtravelokaservice.service.implement.EmailService;
import com.cgtravelokaservice.service.IUserService;
import com.cgtravelokaservice.service.implement.TokenService;
import com.cgtravelokaservice.service.implement.TokenTypeService;
import com.cgtravelokaservice.util.RandomDigitsGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.thymeleaf.context.Context;

import java.sql.Timestamp;



@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenTypeService tokenTypeService;


    @PostMapping(value = "/add")
    public ResponseEntity<?> register(@Validated @RequestBody User user, BindingResult bindingResult,
                                      @RequestParam(name = "role", defaultValue = "ROLE_CUSTOMER") String role) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Check regex");
        }
        if (userRepo.findByUsername(user.getUsername()).isEmpty()
                && userRepo.findByEmail(user.getEmail()).isEmpty()
                && userRepo.findByPhone(user.getPhone()).isEmpty()) {
            if (userService.addUser(user, role)) {
                tokenService.disableTokenByType(user.getEmail(), "CONFIRM_ACCOUNT");
                String code = RandomDigitsGenerator.generate(6);
                Token token =
                        Token.builder().code(code).user(user).createdTime(new Timestamp(System.currentTimeMillis())).
                        expiredTime(new Timestamp(System.currentTimeMillis() + 15 * 60 * 1000)).
                        type(tokenTypeService.findByName("CONFIRM_ACCOUNT").get()).status(true).build();
                tokenService.add(token);
                Context context = new Context();
                context.setVariable("message", code);
                emailService.sendMail("Traveloka -Account Confirm", user.getEmail(), context, "email-template");
                return ResponseEntity.ok("Sent code for confirming account " + code);
            } else {
                return ResponseEntity.internalServerError().body("Error during saving user");
            }
        }
        return ResponseEntity.badRequest().body("User already exist");
    }

    @PostMapping("/validateCode")
    public ResponseEntity<?> validateCode(@RequestBody @Validated ValidateCodeRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid request. Check constraints or field names");
        }
        if (tokenService.isTokenValid(request.getEmail(), request.getCode())) {
            if (userService.activeUser(request.getEmail())) {
                return ResponseEntity.ok("Register success");
            } else {
                return ResponseEntity.internalServerError().body("Error during add user");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Invalid code");
        }
    }

    @PostMapping("/check/{type}")
    public ResponseEntity<?> check(@RequestBody String username, @PathVariable("type") String type) {
        System.out.println(username);
        if (!userService.checkUserExisted(type, username)) {
            return ResponseEntity.ok("Tên người dùng có thể sử dụng");
        } else {
            return ResponseEntity.status(406).body("Tên người dùng đã được đăng ký");
        }
    }

}
