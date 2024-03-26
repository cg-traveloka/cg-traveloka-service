package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.request.EmailRequest;
import com.cgtravelokaservice.dto.request.ResetPassRequest;
import com.cgtravelokaservice.dto.request.ValidateCodeRequest;
import com.cgtravelokaservice.entity.token.Token;
import com.cgtravelokaservice.entity.user.User;
import com.cgtravelokaservice.service.implement.EmailService;
import com.cgtravelokaservice.service.IUserService;
import com.cgtravelokaservice.service.implement.TokenService;
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


    @PostMapping({"/sendCode", "/sendCodeAgain"})
    public ResponseEntity<?> sendCode(@RequestBody @Validated EmailRequest request) {
        if (userService.checkValidUser(request.getEmail())) {
            Optional<User> userOptional = userService.findValidUserByAccountName(request.getEmail());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Token token = tokenService.generateOrRefreshCode(user);
                Context context = new Context();
                context.setVariable("message", token.getCode());
                emailService.sendMail("Traveloka - Code for reset password", user.getEmail(), context, "email" +
                        "-template");
                return ResponseEntity.ok("Sent code for reset pass success. Code: " + token.getCode());

            } else {
                return ResponseEntity.status(404).body("Người dùng không tồn tại");
            }

        }
        return ResponseEntity.badRequest().body("Email chưa đăng ký");
    }

    @PostMapping("/validateCode")
    public ResponseEntity<?> validateCode(@RequestBody @Validated ValidateCodeRequest request,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Yêu cầu không hợp lệ. Vui lòng xem lại định dạng.");
        }

        if (tokenService.isCodeValid(request.getEmail(), request.getCode())) {
            return ResponseEntity.ok("Validate code success");

        } else {
            return ResponseEntity.badRequest().body("Xác thực mã thất bại");
        }
    }


    @PostMapping("/resetPass")
    public ResponseEntity<?> resetPass(@RequestBody @Validated ResetPassRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Yêu cầu không hợp lệ. Vui lòng xem lại định dạng.");
        }
        if (userService.updateUserPass(request.getEmail(), request.getNewPass())) {
            return ResponseEntity.ok("Reset mật khẩu thành công");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Reset mật khẩu thất bại");
        }
    }


}
