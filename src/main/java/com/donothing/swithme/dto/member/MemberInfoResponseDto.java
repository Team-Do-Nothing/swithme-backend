package com.donothing.swithme.dto.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberInfoResponseDto {
    private Long memberId;
    private String name;
    private String nickname;

    @Builder
    public MemberInfoResponseDto(Long memberId, String name, String nickname) {
        this.memberId = memberId;
        this.name = name;
        this.nickname = nickname;
    }
}
