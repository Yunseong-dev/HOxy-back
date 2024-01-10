package com.example.demo.main.auth.service;

import com.example.demo.main.auth.dto.LoginDto;
import com.example.demo.main.auth.dto.LoginResultDto;
import com.example.demo.main.user.model.User;
import com.example.demo.main.user.repository.UserRepository;
import com.example.demo.main.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public LoginResultDto login(LoginDto dto) {
        // 1. dto.id 로 사용자를 불러와야 한다.
        User user = userRepository.findById(dto.getId()).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        // 2. 비밀번호 동일 여부 확인
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        return new LoginResultDto(jwtUtil.generateToken(user));
    }
}
