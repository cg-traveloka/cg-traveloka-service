package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.request.EmailRequest;
import com.cgtravelokaservice.dto.request.ResetPassRequest;
import com.cgtravelokaservice.dto.request.ValidateCodeRequest;
import com.cgtravelokaservice.entity.token.Token;
import com.cgtravelokaservice.entity.user.User;
import com.cgtravelokaservice.service.EmailService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.util.Optional;


@Controller
@RequestMapping("/forgetPass")
public class ForgetPassController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private IUserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenTypeService tokenTypeService;


    @PostMapping({"/sendCode", "/sendCodeAgain"})
    public ResponseEntity<?> sendCode(@RequestBody @Validated EmailRequest request) {
        if (userService.checkValidUser(request.getEmail())) {
            Optional<User> userOptional = userService.findValidUserByAccountName(request.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                tokenService.disableTokenByType(user.getEmail(), "FORGET_PASSWORD");
                String code = RandomDigitsGenerator.generate(6);
                Token token =
                        Token.builder().code(code).user(user).createdTime(new Timestamp(System.currentTimeMillis())).
                                expiredTime(new Timestamp(System.currentTimeMillis() + 15 * 60 * 1000)).
                                type(tokenTypeService.findByName("FORGET_PASSWORD").get()).status(true).build();

                tokenService.add(token);
                Context context = new Context();
                context.setVariable("message", code);
                emailService.sendMail("Traveloka - Code for reset password", user.getEmail(), context, "email" +
                        "-template");
                return ResponseEntity.ok("Sent code for reset pass success. Code: " + code);

            } else {
                return ResponseEntity.status(404).body("User not existed");
            }

        }
        return ResponseEntity.badRequest().body("Email not sign in");
    }

    @PostMapping("/validateCode")
    public ResponseEntity<?> validateCode(@RequestBody @Validated ValidateCodeRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid request. Check regex or field name" + bindingResult.getFieldError());
        }
        if (tokenService.isTokenValid(request.getEmail(), request.getCode())) {
            return ResponseEntity.ok("Validate code success");
        } else {
            return ResponseEntity.badRequest().body("Validate code fail");
        }
    }

    @PostMapping("/resetPass")
    public ResponseEntity<?> resetPass(@RequestBody @Validated ResetPassRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid request. Check regex or field name " + bindingResult.getAllErrors());
        }
        if (userService.updateUserPass(request.getEmail(), request.getNewPass())) {
            return ResponseEntity.ok("Reset pass success");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during update user");
        }
    }


}
