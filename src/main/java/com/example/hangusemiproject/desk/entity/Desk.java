package com.example.hangusemiproject.desk.entity;


import com.example.hangusemiproject.auth.entity.User;
import com.example.hangusemiproject.desk.dto.DeskRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Desk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String profile;

    @Column
    private String deskImg;

    @Column
    private String description;

    @Column
    private String username;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Desk(DeskRequestDto deskRequestDto,User user) {
        this.username = user.getName();
        this.profile = deskRequestDto.getProfile();
        this.deskImg = deskRequestDto.getDeskImg();
        this.description = deskRequestDto.getDescription();
        this.user = user;
    }

    public void update(DeskRequestDto deskRequestDto) {

        this.profile = deskRequestDto.getProfile();
        this.deskImg = deskRequestDto.getDeskImg();
        this.description = deskRequestDto.getDescription();
    }
}
