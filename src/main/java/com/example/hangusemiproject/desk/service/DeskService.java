package com.example.hangusemiproject.desk.service;


import com.example.hangusemiproject.auth.entity.User;
import com.example.hangusemiproject.auth.jwt.JwtUtil;
import com.example.hangusemiproject.auth.repository.UserRepository;
import com.example.hangusemiproject.desk.dto.DeskDetailsResponseDto;
import com.example.hangusemiproject.desk.dto.DeskRequestDto;
import com.example.hangusemiproject.desk.dto.DeskResponseDto;
import com.example.hangusemiproject.desk.dto.UserInfoResponseDto;
import com.example.hangusemiproject.desk.entity.Desk;
import com.example.hangusemiproject.desk.repository.DeskRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeskService {

    private final DeskRepository deskRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public DeskDetailsResponseDto getDesk(Long deskId) {
        Desk desk = findDesk(deskId);
        return new DeskDetailsResponseDto(desk);
    }

    public List<DeskResponseDto> getDesksList() {
        return deskRepository.findAll().stream().map(DeskResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public String createDesk(DeskRequestDto deskRequestDto, User user) {
       deskRepository.save( new Desk(deskRequestDto,user) );
        return "나만의 책상을 만들었습니다.";
    }

    @Transactional
    public DeskDetailsResponseDto updateDesk(Long deskId, DeskRequestDto deskRequestDto, User user) {
        Desk desk = findDesk(deskId);
        if (!isDeskOwner(desk,user)){
            throw new IllegalArgumentException("책상을 수정할 권한이 없습니다.");
        }

        desk.update(deskRequestDto);
        return new DeskDetailsResponseDto(desk);
    }

    @Transactional
    public String deleteDesk(Long deskId,User user) {
        Desk desk = findDesk(deskId);
        if (!isDeskOwner(desk,user)){
            throw new IllegalArgumentException("책상을 수정할 권한이 없습니다.");
        }
        deskRepository.delete(desk);

        return "삭제 되었습니다.";
    }

    public UserInfoResponseDto getUserInfo(HttpServletRequest request) {
        String token = jwtUtil.getJwtFromHeader(request);
        jwtUtil.validateToken(token);
        Claims userInfo = jwtUtil.getUserInfoFromToken(token);

        User user = userRepository.findByUserId(userInfo.getSubject()).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 유저입니다.")
        );

        Long deskId = Optional.ofNullable(user.getDesk())
                .map(Desk::getId)
                .orElse(null);

        return new UserInfoResponseDto(userInfo.getSubject(), user.getName(), deskId);
    }


    private Desk findDesk(Long deskId) {
        return deskRepository.findById(deskId).orElseThrow(() ->
                new IllegalArgumentException("존재 하지 않는 책상입니다.")
        );
    }

    public boolean isDeskOwner(Desk desk, User user) {
        return desk.getUser().getUserId().equals(user.getUserId());
    }


}