package com.donothing.swithme.controller;

import com.donothing.swithme.dto.challenge.ChallengeDetailResponseDto;
import com.donothing.swithme.dto.challenge.ChallengeRegisterRequestDto;
import com.donothing.swithme.dto.challenge.ChallengeRegisterResponseDto;
import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.service.ChallengeService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ResponseDto<List<ChallengeDetailResponseDto>>> getMyChallenge(@AuthenticationPrincipal UserDetails user, @PathVariable String studyId)
    {
        return new ResponseEntity<>(new ResponseDto<>(200, "챌린지 조회 성공",
                challengeService.getMyChallenge(user.getUsername(), studyId)),
                HttpStatus.OK);
    }
}
