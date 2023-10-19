package com.donothing.swithme.controller;

import com.donothing.swithme.dto.member.MemberJoinDto;
import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberJoinDto memberJoinDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new IllegalStateException("유효성 검사 실패");
        } else {
            if (memberJoinDto.getPassword().equals(memberJoinDto.getPasswordConfirm())) {
                throw new IllegalStateException("비밀번호가 일치하지 않습니다");
            }
            return new ResponseEntity<>(new ResponseDto(200, "회원가입 성공",
                    authService.signup(memberJoinDto)), HttpStatus.OK);
        }
    }
}
