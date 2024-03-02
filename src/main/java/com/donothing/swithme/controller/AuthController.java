package com.donothing.swithme.controller;

import com.donothing.swithme.dto.member.MemberJoinRequestDto;
import com.donothing.swithme.dto.member.MemberLoginRequestDto;
import com.donothing.swithme.dto.member.MemberNickNameDuplicateDto;
import com.donothing.swithme.dto.member.TokenDto;
import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.service.member.AuthService;
import com.donothing.swithme.service.member.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/api/v1/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @ApiOperation(value = "회원가입", notes = "회원가입 성공 시 이메일을 반환해줍니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "회원가입 성공"),
            @ApiResponse(code = 400, message = "잘못된 접근입니다"),
            @ApiResponse(code = 409, message = "이미 존재하는 이메일입니다"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
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

    @ApiOperation(value = "로그인", notes = "로그인 성공 시 accessToken과 refreshToken을 반환해줍니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그인 성공"),
            @ApiResponse(code = 400, message = "비밀번호 오류"),
            @ApiResponse(code = 404, message = "존재하지 않은 이메일입니다."),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequestDto memberLoginDto) {
        TokenDto tokenDto = authService.login(memberLoginDto);
        return new ResponseEntity<ResponseDto>(new ResponseDto<>(200, "로그인 성공", tokenDto),
                HttpStatus.OK);
    }

    /**
     * 로그아웃
     */
    @ApiOperation(value = "로그아웃", notes = "로그아웃 시 accessToken과 refreshToken은 더이상 사용할 수 없습니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "로그아웃 성공"),
            @ApiResponse(code = 500, message = "서버 에러")
    })
    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {

        String accessToken = request.getHeader("Authorization").substring(7);
//        Long memberId = SecurityUtil.getCurrentMemberId();
        authService.logout(accessToken);

        return new ResponseEntity<>(new ResponseDto<Object>(200, "로그아웃 완료", null),
                HttpStatus.OK);
    }

    @ApiOperation(value = "닉네임 중복 검사", notes = "닉네임 중복 시 에러 발생")
    @ApiResponses({
            @ApiResponse(code = 200, message = "사용가능한 닉네임입니다."),
            @ApiResponse(code = 409, message = "중복된 닉네임이 존재합니다.")
    })
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
            if (memberService.existsByNickName(memberNickNameDuplicateDto.getNickname())) {
                throw new IllegalStateException("이미 존재하는 닉네임입니다.");
            } else {
                return new ResponseDto(200, "닉네임 사용가능", "사용가능한 닉네임입니다.");
            }
        }
    }
}
