package com.donothing.swithme.controller;

import com.donothing.swithme.domain.Member;
import com.donothing.swithme.dto.member.MemberDetailResponseDto;
import com.donothing.swithme.dto.member.MemberMyDetailResponseDto;
import com.donothing.swithme.dto.member.MemberUpdateDto;
import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.service.member.MemberService;
import com.donothing.swithme.util.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/api/v1/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value = "멤버(자신) 회원프로필 조회", notes = "사용자 자신의 회원 프로필 정보를 조회하는 API 입니다.")
    @GetMapping("/")
    public ResponseEntity getMemberDetail() {
        Long memberId = SecurityUtil.getCurrentMemberId();
        Member member = memberService.findByMemberId(memberId);

        MemberMyDetailResponseDto myDetailResponseDto = MemberMyDetailResponseDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .name(member.getName())
                .nickname(member.getNickname())
                .gender(member.getGender())
                .birthdate(member.getBirthdate())
                .phone(member.getPhone())
                .introduce(member.getIntroduce())
                .temperature(member.getTemperature())
                .build();

        return new ResponseEntity<ResponseDto>(new ResponseDto<MemberMyDetailResponseDto>(200, "success",
                myDetailResponseDto), HttpStatus.OK);
    }

    @ApiOperation(value = "멤버(특정 회원) 회원프로필 조회", notes = "사용자가 특정 회원의 프로필 정보를 조회하는 API 입니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseDto<MemberDetailResponseDto>> getMember(@PathVariable Long memberId) {
        Member member = memberService.findByMemberId(memberId);

        MemberDetailResponseDto detailResponseDto = MemberDetailResponseDto.builder()
                .nickname(member.getNickname())
                .gender(member.getGender())
                .birthdate(member.getBirthdate())
                .introduce(member.getIntroduce())
                .temperature(member.getTemperature())
                .build();

        return new ResponseEntity<>(new ResponseDto<>(200, "멤버 조회 성공", detailResponseDto),
                HttpStatus.OK);
    }

//    @ApiOperation(value = "멤버 전체 회원프로필 조회", notes = "사용자가 다른 회원들의 프로필을 전체 조회하는 API입니다.")
//    @GetMapping("/list/page")
//    public ResponseEntity<?> getMembers(String friend) {
//
//    }

    @ApiOperation(value = "회원 프로필 수정")
    @PutMapping("/")
    public ResponseEntity updateMember(@Valid MemberUpdateDto memberUpdateDto) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        memberService.updateMember(memberId, memberUpdateDto);

        return new ResponseEntity<ResponseDto>(new ResponseDto(200, "success", "회원 정보 수정 완료"),
                HttpStatus.OK);
    }
}
