package com.example.demo.main.user.service;

import com.example.demo.main.user.dto.CreateUserDto;

import com.example.demo.main.user.dto.UpdateUserDto;
import com.example.demo.main.user.model.User;
import com.example.demo.main.user.repository.UserRepository;
import com.example.demo.main.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;

    public User createUser(CreateUserDto dto) {
        LocalDateTime now = LocalDateTime.now();
        if (userRepository.existsById(dto.getId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        User user = new User(
                dto.getId(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getName(),
                dto.getGender(),
                dto.getBirth(),
                dto.getHeight(),
                dto.getWeight(),
                dto.getDisease1(),
                now,
                now
        );

        return userRepository.save(user);
    }

    public User getMyProfileByToken(String token) {
        String userId = jwtUtil.getSubject(token);
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        return userRepository.findById(userId).get();
    }

    public User updateUser(UpdateUserDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User) {
            User user = (User) principal;

            // 나머지 업데이트 로직은 그대로 유지
            user.setName(dto.getName());
            user.setGender(dto.getGender());
            user.setBirth(dto.getBirth());
            user.setHeight(dto.getHeight());
            user.setWeight(dto.getWeight());
            user.setDisease1(dto.getDisease1());
            user.setUpdatedAt(LocalDateTime.now());

            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("사용자 정보를 가져올 수 없습니다.");
        }
    }



    public User getOneUser(String id){
        return userRepository.getById(id);
    }

}