package com.sparta.lv1memo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "알파벳 소문자(a~z)와 숫자(0~9)로 구성된 4~10자의 문자열이어야 합니다.")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,15}$", message = "알파벳 대소문자(a~z, A~Z)와 숫자(0~9)로 구성된 8~15자의 문자열이어야 합니다.")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}