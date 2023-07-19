package com.example.hangusemiproject.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @Size(min = 4, max = 10, message = "아이디는 4자이상 10자 이하로 입력해주세요.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "유효하지 않은 아이디 형식입니다.")
    @NotBlank(message = "아이디를 입력해주세요")
    private String userId;


    @Size(min = 4, max = 15, message = "비밀번호는 4자이상 15자 이하로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{4,15}$", message = "비밀번호는 알파벳, 숫자, 특수문자를 포함해야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String userPassword;

    @Size(min = 1, max = 20, message = "이름은 1자이상 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,20}$", message = "유효하지 않은 이름 형식입니다.")
    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[A-Za-z]+$", message = "유효하지 않은 이메일 형식입니다.")
    @NotBlank(message = "이메일을 입력 해주세요")
    private String email;
}
