package com.example.demo.main.auth.provider;


import com.example.demo.main.auth.authentication.JwtAuthentication;
import com.example.demo.main.user.model.User;
import com.example.demo.main.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String credentials = (String) authentication.getCredentials();
        String userId = jwtUtil.getSubject(credentials);
        User user = (User) userDetailsService.loadUserByUsername(userId);

        return new JwtAuthentication(credentials, user, true);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication == JwtAuthentication.class;
    }

}
