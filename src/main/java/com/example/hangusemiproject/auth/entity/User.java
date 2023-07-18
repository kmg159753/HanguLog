package com.example.hangusemiproject.auth.entity;

import com.example.hangusemiproject.auth.dto.SignupRequestDto;
import com.example.hangusemiproject.desk.entity.Desk;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true,nullable = false)
    private String userId;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role = UserRoleEnum.USER;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Desk desk;

    public User(SignupRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.userPassword = requestDto.getUserPassword();
        this.name = requestDto.getName();
        this.email = requestDto.getEmail();
    }

    public User(String userId, String userPassword, String name, String email) {

        this.userId = userId;
        this.userPassword = userPassword;
        this.name = name;
        this.email = email;
    }
}
