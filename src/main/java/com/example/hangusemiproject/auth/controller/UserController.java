package com.example.hangusemiproject.auth.controller;


import com.example.hangusemiproject.auth.dto.SignupRequestDto;
import com.example.hangusemiproject.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService authService;


    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody SignupRequestDto signupRequestDto, BindingResult result){
        Map<String, String> response = new HashMap<>();

        if(result.hasErrors()) {
            String errorMsg = result.getFieldError().getDefaultMessage();

            response.put("result", "fail");
            response.put("msg", errorMsg);

            return ResponseEntity.badRequest().body(response);
        }

        String msg = authService.signup(signupRequestDto);

        response.put("result", "success");
        response.put("msg", msg);

        return ResponseEntity.ok(response);
    }
}
