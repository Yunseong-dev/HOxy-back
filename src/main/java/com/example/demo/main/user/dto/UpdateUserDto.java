package com.example.demo.main.user.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateUserDto {
    private String password;
    private String name;
    private String gender;
    private String birth;
    private String height;
    private String weight;
    private String disease1;
    private LocalDateTime updatedAt;
}
