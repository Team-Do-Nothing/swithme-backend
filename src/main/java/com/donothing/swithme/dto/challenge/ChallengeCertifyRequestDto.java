package com.donothing.swithme.dto.challenge;

import com.donothing.swithme.domain.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ChallengeCertifyRequestDto {

    @ApiModelProperty(hidden = true)
    private Long memberId;

    private Long challengeId;
    private String description;

    public ChallengeLog toEntity(String s3Url, ApproveStatus status) {
        return ChallengeLog.builder()
                .member(new Member(this.memberId))
                .challenge(new Challenge(this.challengeId))
                .s3Url(s3Url)
                .challengeLogStatus(ChallengeLogStatus.ACTIVE)
                .challengeLogApproveStatus(status)
                .build();
    }
}
