package com.example.demo.main.auth.controller;

import com.example.demo.main.auth.dto.LoginDto;
import com.example.demo.main.auth.dto.LoginResultDto;
import com.example.demo.main.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResultDto loginResultDto(
            @RequestBody LoginDto dto
    ) {
        return authService.login(dto);
    }
}
