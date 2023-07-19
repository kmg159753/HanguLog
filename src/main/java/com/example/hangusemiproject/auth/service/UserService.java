package com.example.hangusemiproject.auth.service;

import com.example.hangusemiproject.auth.dto.LoginRequestDto;
import com.example.hangusemiproject.auth.dto.SignupRequestDto;
import com.example.hangusemiproject.auth.entity.User;
import com.example.hangusemiproject.auth.jwt.JwtUtil;
import com.example.hangusemiproject.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.hangusemiproject.auth.jwt.JwtUtil.AUTHORIZATION_HEADER;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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

    public String login(LoginRequestDto userId, HttpServletResponse response) {
        User user = userRepository.findByUserId(userId.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        if (!passwordEncoder.matches(userId.getUserPassword(), user.getUserPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }

        String token = jwtUtil.createToken(user.getUserId(),user.getRole());
        response.setHeader(AUTHORIZATION_HEADER, token);
        return token;
    }
}
