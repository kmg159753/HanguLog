package com.example.hangusemiproject.desk.dto;

import com.example.hangusemiproject.desk.entity.Desk;
import lombok.Getter;

@Getter
public class DeskDetailsResponseDto {

    String name;

    String description;

    String profile;

    String deskImg;

    public DeskDetailsResponseDto(Desk desk) {
        this.name = desk.getUsername();
        this.description = desk.getDescription();
        this.profile = desk.getProfile();
        this.deskImg = desk.getDeskImg();
    }
}
