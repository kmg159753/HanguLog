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
    public String createDesk(DeskRequestDto deskRequestDto, HttpServletRequest request) {

        String tokenFromRequest = jwtUtil.getJwtFromHeader(request);
        String userId = jwtUtil.getUserInfoFromToken(tokenFromRequest).getSubject();


        User user = userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("항해원이 아닙니다.")
        );

        if(deskRequestDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("당신의 책상을 소개해주세요!");
        }else if(deskRequestDto.getDeskImg().isEmpty()){
            throw new IllegalArgumentException("당신의 책상을 보여주세요.");
        }

        deskRepository.save( new Desk(deskRequestDto,user) );
        return "나만의 책상을 만들었습니다.";
    }

    @Transactional
    public DeskDetailsResponseDto updateDesk(Long deskId, DeskRequestDto deskRequestDto, HttpServletRequest request) {
        Desk desk = findDesk(deskId);

        User user = getUserFromRequest(request);

        if (!isDeskOwner(desk, user)){
            throw new IllegalArgumentException("당신의 책상이 이닙니다!!");
        }

        if(deskRequestDto.getDescription().isEmpty()) {
            throw new IllegalArgumentException("당신의 책상을 소개해주세요!");
        }else if(deskRequestDto.getDeskImg().isEmpty()){
            throw new IllegalArgumentException("당신의 책상을 보여주세요.");
        }

        desk.update(deskRequestDto);
        return new DeskDetailsResponseDto(desk);
    }

    @Transactional
    public String deleteDesk(Long deskId,HttpServletRequest request) {
        Desk desk = findDesk(deskId);
        User user = getUserFromRequest(request);
        if (!isDeskOwner(desk,user)){
            throw new IllegalArgumentException("당신의 책상이 이닙니다!!");
        }
        user.removeDesk();
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

    private User getUserFromRequest(HttpServletRequest request) {
        String tokenFromRequest = jwtUtil.getJwtFromHeader(request);
        String userId = jwtUtil.getUserInfoFromToken(tokenFromRequest).getSubject();

        return userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 사람입니다.")
        );
    }

    private boolean isDeskOwner(Desk desk, User user) {
        return desk.getUser().getUserId().equals(user.getUserId());
    }
}
