package com.donothing.swithme.dto.challenge;

import com.donothing.swithme.domain.Challenge;
import com.donothing.swithme.domain.ChallengeStatus;
import com.donothing.swithme.domain.MemberChallenge;
import com.donothing.swithme.dto.member.MemberInfoResponseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class ChallengeDetailResponseDto {
    @ApiModelProperty(value = "챌린지 아이디", example = "1")
    private final long challengeId;

    @ApiModelProperty(value = "스터디 제목")
    private final String title;

    @ApiModelProperty(value = "챌린지 목표")
    private final String goal;

    @ApiModelProperty(value = "챌린지 시작날짜")
    private final String startDate;

    @ApiModelProperty(value = "챌린지 종료날짜")
    private final String endDate;

    @ApiModelProperty(value = "챌린지 참가금액")
    private final int challengeFee;

    @ApiModelProperty(value = "챌린지 인증방법")
    private final String checkFormat;

    @ApiModelProperty(value = "챌린지 타입")
    private final ChallengeStatus challengeStatus; // DAILY, SPECIAL

    @ApiModelProperty(value = "챌린지 전체 금액")
    private final int challengeFeeAll;

    public ChallengeDetailResponseDto(Challenge challenge) {
        this.challengeId = challenge.getChallengeId();
        this.title = challenge.getTitle();
        this.goal = challenge.getGoal();
        this.startDate = challenge.getStartDate();
        this.endDate = challenge.getEndDate();
        this.challengeFee = challenge.getChallengeFee();
        this.checkFormat = challenge.getCheckFormat();
        this.challengeStatus = challenge.getChallengeStatus();
        this.challengeFeeAll = challenge.getChallengeFeeAll();
    }

    public ChallengeDetailResponseDto(MemberChallenge memberChallenge) {
        this.challengeId = memberChallenge.getChallenge().getChallengeId();
        this.title = memberChallenge.getChallenge().getTitle();
        this.goal = memberChallenge.getChallenge().getGoal();
        this.startDate = memberChallenge.getChallenge().getStartDate();
        this.endDate = memberChallenge.getChallenge().getEndDate();
        this.challengeFee = memberChallenge.getChallenge().getChallengeFee();
        this.checkFormat = memberChallenge.getChallenge().getCheckFormat();
        this.challengeStatus = memberChallenge.getChallenge().getChallengeStatus();
        this.challengeFeeAll = memberChallenge.getChallenge().getChallengeFeeAll();
    }
}
