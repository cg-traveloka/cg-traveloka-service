package com.cgtravelokaservice.controller;

import com.cgtravelokaservice.dto.request.EmailRequest;
import com.cgtravelokaservice.dto.request.ResetPassRequest;
import com.cgtravelokaservice.dto.request.ValidateCodeRequest;
import com.cgtravelokaservice.entity.token.Token;
import com.cgtravelokaservice.entity.user.User;
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
                emailService.sendMail("Traveloka - Mã để reset mật khẩu", user.getEmail(), context, "email" +
                        "-template");
                return ResponseEntity.ok("Đã gửi mã để reset mật khẩu. Mã: " + code);

            } else {
                return ResponseEntity.status(404).body("Người dùng không tồn tại");
            }

        }
        return ResponseEntity.badRequest().body("Email chưa đăng ký");
    }

    @PostMapping("/validateCode")
    public ResponseEntity<?> validateCode(@RequestBody @Validated ValidateCodeRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Yêu cầu không hợp lệ. Vui lòng xem lại định dạng.");
        }
        if (tokenService.isTokenValid(request.getEmail(), request.getCode())) {
            return ResponseEntity.ok("Xác thực mã thành công");
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
