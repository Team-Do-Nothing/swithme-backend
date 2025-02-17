package com.donothing.swithme.dto.member;

import com.donothing.swithme.domain.MemberStudy;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberListResponseDto {

    private Long memberId;
    private String nickname;
    private String s3Url;

    public MemberListResponseDto(MemberStudy memberStudy) {
        this.memberId = memberStudy.getMember().getMemberId();
        this.nickname = memberStudy.getMember().getNickname();
        this.s3Url = memberStudy.getMember().getS3Url();
    }
}
