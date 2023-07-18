package com.example.hangusemiproject.auth.service;

import com.example.hangusemiproject.auth.dto.SignupRequestDto;
import com.example.hangusemiproject.auth.entity.User;
import com.example.hangusemiproject.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String signup( SignupRequestDto requestDto) {

        Optional<User> checkUserId =  userRepository.findByUserId(requestDto.getUserId());
        if(checkUserId.isPresent()){
            throw new IllegalArgumentException("이미 존재하는 사용자 아이디입니다.");
        }

        Optional<User> checkEmail = userRepository.findByEmail(requestDto.getEmail());
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        User user = new User(requestDto.getUserId(),passwordEncoder.encode(requestDto.getUserPassword()),requestDto.getName(),requestDto.getEmail());
        userRepository.save(user);

        return "회원가입에 성공했습니다";
    }
}
