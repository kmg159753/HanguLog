package com.example.hangusemiproject.auth.controller;


import com.example.hangusemiproject.auth.dto.IdMatchingDto;
import com.example.hangusemiproject.auth.dto.LoginRequestDto;
import com.example.hangusemiproject.auth.dto.SignupRequestDto;
import com.example.hangusemiproject.auth.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "UserController")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService authService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        Map<String, String> response = new HashMap<>();

        String msg = authService.signup(signupRequestDto);
        log.info("msg : {}", msg);

        response.put("result", "success");
        response.put("msg", msg);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        Map<String, String> response = new HashMap<>();

        try {
            String token = authService.login(loginRequestDto, httpServletResponse);

            response.put("result", "success");
            response.put("msg", "로그인에 성공했습니다.");
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            response.put("result", "fail");
            response.put("msg", e.getMessage());

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("result", "fail");
            response.put("msg", "로그인 과정에서 일시적인 오류가 발생했습니다.");

            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/signup/Id")
    public ResponseEntity<Map<String, Boolean>> isIdMatch(@RequestBody IdMatchingDto idMatchingDto){
        Map<String, Boolean> response = new HashMap<>();

        response.put("isMatchId", authService.isIdMatch(idMatchingDto.getUserId()));

        return ResponseEntity.ok(response);
    }
}
