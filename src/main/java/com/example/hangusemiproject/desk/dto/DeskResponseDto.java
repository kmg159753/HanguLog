package com.example.hangusemiproject.desk.dto;


import com.example.hangusemiproject.desk.entity.Desk;
import lombok.Getter;


@Getter
public class DeskResponseDto {
    Long deskId;

    String name;

    String profile;

    public DeskResponseDto(Desk desk) {
        this.deskId = desk.getId();
        this.name = desk.getUsername();
        this.profile = desk.getProfile();
    }
}
