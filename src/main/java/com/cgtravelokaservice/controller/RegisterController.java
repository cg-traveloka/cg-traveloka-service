package com.codegym.controller;


import com.codegym.model.ForgetPassMail;
import com.codegym.model.MailMessage;
import com.codegym.model.entity.Role;
import com.codegym.model.entity.Token;
import com.codegym.model.entity.User;
import com.codegym.repository.UserRepo;
import com.codegym.service.IUserService;
import com.codegym.service.impl.MailService;
import com.codegym.service.impl.RoleService;
import com.codegym.service.impl.TokenService;
import com.codegym.service.impl.TokenTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashSet;
import java.util.Set;


@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MailService mailService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenTypeService tokenTypeService;

    @GetMapping
    public String registerForm() {
        return "register";
    }

    @PostMapping("/add")
    public ResponseEntity<?> register(@Validated @RequestBody User user, BindingResult bindingResult,
                                      HttpServletRequest request,
                                      @RequestParam(name = "role", defaultValue = "ROLE_USER") String role) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Check regex");
        }
        if (userRepo.findByUsername(user.getUsername()).isEmpty()
                && userRepo.findByEmail(user.getEmail()).isEmpty()
                && userRepo.findByPhone(user.getPhone()).isEmpty()) {
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findByName(role).get());
            user.setRoles(roles);
            Role role1 = roleService.findByName(role).get();
            role1.getUsers().add(user);
            userService.save(user);
            tokenService.disableTokenByType(user.getId(), "CONFIRM_ACCOUNT");
            ForgetPassMail mail = new ForgetPassMail();
            Token token = mail.getToken();
            token.setUser(user);
            token.setType(tokenTypeService.findByName("CONFIRM_ACCOUNT").get());
            tokenService.add(token);
            String code = token.getCode();
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("code", code);
            MailMessage message = MailMessage.builder().subject("Traveloka -Account Confirm")
                    .content(code).build();
            mailService.send(message, user.getEmail());
            return ResponseEntity.ok("Sent code for confirming account" + code);
        }
        return ResponseEntity.badRequest().body("User already exist");
    }

    @PostMapping("/validateCode")
    public ResponseEntity<?> validateCode(@RequestBody String codeInput,
                                          @SessionAttribute("code") String code,
                                          @SessionAttribute("user") User user) {
        boolean isValidCode;
        if (code != null) {
            isValidCode = code.equals(codeInput);
        } else {
            isValidCode = tokenService.isTokenValid(user.getEmail(), codeInput);
        }
        if (isValidCode) {
            if (userService.activeUser(user.getEmail())) {
                return ResponseEntity.ok("Register success");
            } else {
                return ResponseEntity.internalServerError().body("Error during add user");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Wrong code");

        }

    }

    @PostMapping("/check/{type}")
    public ResponseEntity<?> check(@RequestBody String username, @PathVariable("type") String type) {
        if (!userService.checkUserExisted(type, username)) {
            return ResponseEntity.ok("Accept username");
        } else {
            return ResponseEntity.status(406).body("Username already register");
        }
    }
}
