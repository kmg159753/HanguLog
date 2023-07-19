package com.example.hangusemiproject.desk.controller;


import com.example.hangusemiproject.desk.dto.DeskDetailsResponseDto;
import com.example.hangusemiproject.desk.dto.DeskRequestDto;
import com.example.hangusemiproject.desk.dto.DeskResponseDto;
import com.example.hangusemiproject.desk.dto.UserInfoResponseDto;
import com.example.hangusemiproject.desk.service.DeskService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/desks")
@RequiredArgsConstructor
@Slf4j
public class DeskController {

    private final DeskService deskService;

    //메인페이지 - 책상정보 전체 조회

    @PostMapping()
    public ResponseEntity<Map<String, String>> createDesk(@RequestBody DeskRequestDto deskRequestDto , HttpServletRequest request){
        String msg = deskService.createDesk(deskRequestDto,request);
        Map<String, String> response = new HashMap<>();

        response.put("result", "success");
        response.put("msg", msg);

        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public List<DeskResponseDto> getDesksList(){
        return deskService.getDesksList();
    }

    //책상정보 상세 조회
    @GetMapping("{deskId}")
    public ResponseEntity<DeskDetailsResponseDto> getDesk(@PathVariable Long deskId){
        DeskDetailsResponseDto deskResponseDto = deskService.getDesk(deskId);

        return new ResponseEntity<>(deskResponseDto, HttpStatus.OK);
    }

    @PutMapping("{deskId}")
    public ResponseEntity<DeskDetailsResponseDto> updateDesk(@PathVariable Long deskId, @RequestBody DeskRequestDto deskRequestDto,
                                        HttpServletRequest request){
        DeskDetailsResponseDto deskDetailsResponseDto = deskService.updateDesk(deskId, deskRequestDto, request);
        return new ResponseEntity<>(deskDetailsResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("{deskId}")
    public ResponseEntity<Map<String, String>> deleteDesk(@PathVariable Long deskId,
                                                          HttpServletRequest request){
        String msg = deskService.deleteDesk(deskId,request);

        Map<String, String> response = new HashMap<>();

        response.put("result", "success");
        response.put("msg", msg);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    public ResponseEntity<UserInfoResponseDto> getUserInfo(HttpServletRequest request){
        UserInfoResponseDto userInfoResponseDto = deskService.getUserInfo(request);
        return new ResponseEntity<>(userInfoResponseDto, HttpStatus.OK);
    }


}