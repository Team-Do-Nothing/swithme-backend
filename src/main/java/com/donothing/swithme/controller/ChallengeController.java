package com.donothing.swithme.controller;

import com.donothing.swithme.dto.JoinChallengeRequestDto;
import com.donothing.swithme.dto.challenge.*;
import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.service.ChallengeService;
import com.donothing.swithme.util.SecurityUtil;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequestMapping("/api/v1/challenge")
@RestController
@RequiredArgsConstructor
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping
    @ApiOperation(value = "챌린지 생성하기", notes = "챌린지를 생성하는 API 입니다.")
    public ResponseEntity<ResponseDto<ChallengeRegisterResponseDto>> registerChallenge(@RequestBody @Valid
    ChallengeRegisterRequestDto request,
            @AuthenticationPrincipal UserDetails user) {
        request.setMemberId(Long.valueOf(user.getUsername()));
        return new ResponseEntity<>(new ResponseDto<>(201, "챌린지 생성 성공",
                challengeService.registerChallenge(request)),
                HttpStatus.CREATED);
    }

    @GetMapping("/{challengeId}")
    @ApiOperation(value = "챌린지 상세 조회하기", notes = "챌린지를 상세 조회하는 API 입니다.")
    public ResponseEntity<ResponseDto<ChallengeDetailResponseDto>> getDetailChallenge(@PathVariable String challengeId)
    {
        return new ResponseEntity<>(new ResponseDto<>(200, "챌린지 조회 성공",
                challengeService.detailChallengeByChallengeId(challengeId)),
                HttpStatus.OK);
    }

    @GetMapping("/{studyId}/myChallenge")
    @ApiOperation(value = "내가 참여하고 있는 모든 챌린지 조회하기", notes = "내가 참여하고 있는 모든 챌린지 조회하는 API 입니다.")
    public ResponseEntity<ResponseDto<List<ChallengeDetailResponseDto>>> getMyChallenge(
            @AuthenticationPrincipal UserDetails user, @PathVariable String studyId) {
        return new ResponseEntity<>(new ResponseDto<>(200, "챌린지 조회 성공",
                challengeService.getMyChallenge(user.getUsername(), studyId)),
                HttpStatus.OK);
    }

    @PostMapping("/join")
    @ApiOperation(value = "챌린지 참여하기", notes = "챌린지에 참여할 수 있는 API 입니다.")
    public ResponseEntity<ResponseDto<Void>> joinChallenge(@RequestBody @Valid JoinChallengeRequestDto request,
                                                           @AuthenticationPrincipal UserDetails user) {
        request.setMemberId(Long.valueOf(user.getUsername()));
        challengeService.joinChallenge(request);
        return new ResponseEntity<>(new ResponseDto<>(201, "챌린지 참여하기 성공",
                null),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{challengeId}")
    @ApiOperation(value = "해당 챌린지 삭제", notes = "해당 챌린지의 정보를 삭제하는 API 입니다.")
    public ResponseEntity<ResponseDto<Void>> deleteStudy(@PathVariable("challengeId") Long challengeId,
                                                         @AuthenticationPrincipal UserDetails user) {
        challengeService.updateChallengeLogStatusToInactive(challengeId, Long.valueOf(user.getUsername()));
        return new ResponseEntity<>(new ResponseDto<>(204, "챌린지 삭제 성공", null),
                HttpStatus.NO_CONTENT);
    }

    @PostMapping("/daily")
    @ApiOperation(value = "챌린지 데일리 인증하기", notes = "챌린지 데일리 인증용 API 입니다.")
    public ResponseEntity<ResponseDto<ChallengeCertifyResponseDto>> certifyDailyChallenge(
            @ModelAttribute ChallengeCertifyRequestDto certifyRequestDto,
            @RequestPart("file")MultipartFile multipartFile) throws IOException {
        Long memberId = SecurityUtil.getCurrentMemberId();
        certifyRequestDto.setMemberId(memberId);

        challengeService.certifyChallenge(multipartFile, certifyRequestDto);
        return new ResponseEntity<>(new ResponseDto<>(201, "챌린지 데일리 인증 성공", null),
                HttpStatus.CREATED);
    }
}
