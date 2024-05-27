package com.donothing.swithme.dto.challenge;

import lombok.Getter;

@Getter
public class ChallengeRegisterResponseDto {
    private final Long challengeId;

    public ChallengeRegisterResponseDto(long challengeId) {
        this.challengeId = challengeId;
    }
}
