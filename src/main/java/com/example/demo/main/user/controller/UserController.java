package com.example.demo.main.user.controller;

import com.example.demo.main.user.dto.CreateUserDto;
import com.example.demo.main.user.dto.UpdateUserDto;
import com.example.demo.main.user.model.User;
import com.example.demo.main.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public User createUser(
            @RequestBody CreateUserDto dto
    ){
        return userService.createUser(dto);
    }

    @PutMapping("/edit")
    public User updateUser(
            @RequestBody UpdateUserDto dto
    ) {
        return userService.updateUser(dto);
    }

    @GetMapping("/me")
    public User getMyProfile(
            @AuthenticationPrincipal User user
    ) {
        return user;
    }
}

