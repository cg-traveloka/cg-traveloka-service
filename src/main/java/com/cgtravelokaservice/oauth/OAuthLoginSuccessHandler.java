package com.cgtravelokaservice.oauth;


import com.cgtravelokaservice.entity.user.CustomOAuth2User;
import com.cgtravelokaservice.entity.user.Provider;
import com.cgtravelokaservice.entity.user.User;
import com.cgtravelokaservice.entity.user.UserProvider;
import com.cgtravelokaservice.repo.ProviderRepo;
import com.cgtravelokaservice.repo.UserProviderRepo;
import com.cgtravelokaservice.service.implement.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;


@Component

public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    UserService userService;

    @Autowired
    private UserProviderRepo userProviderRepo;

    @Autowired
    private ProviderRepo providerRepo;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String oauth2ClientName = oauth2User.getOauth2ClientName();
        String username = oauth2User.getEmail();
        if (userService.loadUserByUsername(username) != null) {
            if (userService.checkValidUser(username)) {
                System.out.println("login by oauth2 success");
                response.sendRedirect("/login/o2auth/success");
            } else {
                User user = userService.findByEmail(username).get();
                user.setActive(true);
                user.setEnable(true);
                userService.save(user);
                Optional<Provider> providerOptional = providerRepo.findByName(oauth2ClientName.toUpperCase());
                UserProvider userProvider = UserProvider.builder().user(user).provider(providerOptional.get()).build();
                userProviderRepo.save(userProvider);
                response.sendRedirect("/login/o2auth/success");
            }
        } else {
            if (userService.addO2AuthAccount(username, oauth2ClientName)) {
                response.sendRedirect("/login/o2auth/success");
            } else {
                System.out.println("error oauth2");
                response.sendRedirect("/login?error");
            }

        }

    }


}