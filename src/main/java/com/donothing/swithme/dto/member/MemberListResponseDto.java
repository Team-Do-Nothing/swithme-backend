package com.donothing.swithme.dto.member;

import com.donothing.swithme.domain.MemberStudy;
import com.donothing.swithme.domain.StudyRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberListResponseDto {

    private Long memberId;
    private String nickname;
    private String s3Url;
    private StudyRole studyRole;

    public MemberListResponseDto(MemberStudy memberStudy) {
        if (memberStudy == null || memberStudy.getMember() == null ||
                memberStudy.getStudy() == null) {
            throw new IllegalArgumentException("Invalid MemberStudy data");
        }

        this.memberId = memberStudy.getMember().getMemberId();
        this.nickname = memberStudy.getMember().getNickname();
        this.s3Url = memberStudy.getMember().getS3Url();
        this.studyRole = determineStudyRole(memberStudy);
    }

    private StudyRole determineStudyRole(MemberStudy memberStudy) {
        Long studyLeaderId = memberStudy.getStudy().getMember().getMemberId();
        Long currentMemberId = memberStudy.getMember().getMemberId();

        return studyLeaderId.equals(currentMemberId) ?
                StudyRole.LEADER : StudyRole.STUDY_MEMBER;
    }
}
