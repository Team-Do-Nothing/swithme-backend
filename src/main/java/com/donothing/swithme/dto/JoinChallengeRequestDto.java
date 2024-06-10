package com.donothing.swithme.dto;

import com.donothing.swithme.domain.Challenge;
import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.MemberChallenge;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinChallengeRequestDto {
    @ApiModelProperty(hidden = true)
    private Long memberId;

    @NotNull(message = "참여할 챌린지 아이디는 필수입니다.")
    private Long challengeId;

    public MemberChallenge toMemberChallenge() {
        return MemberChallenge.builder()
                .challenge(new Challenge(challengeId))
                .member(new Member(memberId))
                .build();
    }
}
