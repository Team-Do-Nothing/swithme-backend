package com.donothing.swithme.controller;

import com.donothing.swithme.dto.member.MemberJoinRequestDto;
import com.donothing.swithme.dto.member.MemberLoginRequestDto;
import com.donothing.swithme.dto.member.MemberNickNameDuplicateDto;
import com.donothing.swithme.dto.member.TokenDto;
import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid MemberJoinRequestDto memberJoinRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new IllegalStateException("유효성 검사 실패");
        } else {
            if (!memberJoinRequestDto.getPassword().equals(memberJoinRequestDto.getPasswordConfirm())) {
                throw new IllegalStateException("비밀번호가 일치하지 않습니다");
            }
            return new ResponseEntity<>(new ResponseDto(200, "회원가입 성공",
                    authService.signup(memberJoinRequestDto)), HttpStatus.OK);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequestDto memberLoginDto) {
        TokenDto tokenDto = authService.login(memberLoginDto);
        return new ResponseEntity<ResponseDto>(new ResponseDto<>(200, "로그인 성공", tokenDto),
                HttpStatus.OK);
    }

//    @GetMapping("/logout")
//    public ResponseEntity logout(HttpServletRequest request) {
//
//        String accessToken = request.getHeader("Authorization").substring(7);
//        Long memberId = SecurityUtil.
//
//    }

    @PostMapping("/nickname/check")
    public ResponseDto checkNickname(@RequestBody @Valid MemberNickNameDuplicateDto memberNickNameDuplicateDto,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new IllegalStateException("유효성 검사 실패");
        } else {
            if (authService.existsByNickName(memberNickNameDuplicateDto.getNickname())) {
                throw new IllegalStateException("이미 존재하는 닉네임입니다.");
            } else {
                return new ResponseDto(200, "닉네임 사용가능", "사용가능한 닉네임입니다.");
            }
        }
    }
}
