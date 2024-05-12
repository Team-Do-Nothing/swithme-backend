package com.donothing.swithme.dto.member;

import com.donothing.swithme.domain.MemberStudy;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberListResponseDto {

    private Long memberId;
    private String nickname;

    public MemberListResponseDto(MemberStudy memberStudy) {
        this.memberId = memberStudy.getMember().getMemberId();
        this.nickname = memberStudy.getMember().getNickname();
    }
}
