package com.example.hangusemiproject.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
        private String userId;
        private String userPassword;
}
