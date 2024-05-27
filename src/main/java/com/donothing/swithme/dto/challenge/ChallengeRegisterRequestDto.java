package com.donothing.swithme.dto.challenge;

import com.donothing.swithme.domain.Challenge;
import com.donothing.swithme.domain.ChallengeStatus;
import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.MemberChallenge;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChallengeRegisterRequestDto {
    @NotNull(message = "스터디 아이디는 필수입니다.")
    @ApiModelProperty(value = "스터디 아이디", example = "1", required = true)
    private Long studyId;

    @ApiModelProperty(hidden = true)
    private Long memberId;

    @NotNull(message = "챌린지 제목은 필수입니다.")
    @ApiModelProperty(value = "챌린지 제목", required = true)
    private String title;

    @NotNull(message = "챌린지 목표 필수입니다.")
    @ApiModelProperty(value = "챌린지 목표", example = "자격증 취득하기", required = true)
    private String goal;

    @NotNull(message = "챌린지 시작날짜는 필수입니다.")
    @Pattern(regexp = "^\\d{8}$", message="챌린지 시작일은 yyyyMMdd 형식의 날짜로 입력해주세요.")
    @ApiModelProperty(value = "챌린지 시작날짜", required = true)
    private String startDate;

    @NotNull(message = "챌린지 종료날짜는 필수입니다.")
    @Pattern(regexp = "^\\d{8}$", message="챌린지 종료일은 yyyyMMdd 형식의 날짜로 입력해주세요.")
    @ApiModelProperty(value = "챌린지 종료날짜", required = true)
    private String endDate;

    @NotNull(message = "챌린지 참가금액은 필수입니다.")
    @Min(1)
    @ApiModelProperty(value = "챌린지 참가금액", required = true)
    private int challengeFee;

    @NotNull(message = "챌린지 인증방법은 필수입니다.")
    @ApiModelProperty(value = "챌린지 인증방법", example = "아침에 기상한 사진 찍기", required = true)
    private String checkFormat;

    @NotNull(message = "챌린지 타입은 필수입니다.")
    @ApiModelProperty(value = "챌린지 타입", required = true, allowableValues = "DAILY, SPECIAL")
    private ChallengeStatus challengeStatus;

    public Challenge toEntity() {
        return Challenge.builder()
                .title(title)
                .goal(goal)
                .startDate(startDate)
                .endDate(endDate)
                .challengeFee(challengeFee)
                .checkFormat(checkFormat)
                .challengeStatus(challengeStatus)
                .build();
    }

    public MemberChallenge toMemberChallenge(Long challengeId) {
        return MemberChallenge.builder()
                .challenge(new Challenge(challengeId))
                .member(new Member(memberId))
                .build();
    }
}
