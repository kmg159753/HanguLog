package com.example.hangusemiproject.desk.dto;

import lombok.Getter;

@Getter
public class UserInfoResponseDto {

    private String userId;

    private String userName;

    private Long deskId;

    public UserInfoResponseDto(String userId, String userName, Long deskId) {
        this.userId = userId;
        this.userName = userName;
        this.deskId = deskId;
    }
}
