package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.Study;
import com.donothing.swithme.domain.StudyStatus;
import com.donothing.swithme.domain.StudyType;
import com.donothing.swithme.dto.member.MemberInfoResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class StudyDetailResponseDto {
    private Long studyId;

    private MemberInfoResponseDto member;

    private String title;

    private StudyType studyType; // ONLINE, OFFLINE

    private int numberOfMembers; // 목표 인원수

    private String studyInfo;

    private StudyStatus studyStatus; // CURR, COMP, END

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String dateStudyStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String dateStudyEnd;

    public StudyDetailResponseDto(Study study) {
        this.studyId = study.getStudyId();
        this.member = MemberInfoResponseDto.builder().
                memberId(study.getMember().getMemberId()).
                name(study.getMember().getName()).
                nickname(study.getMember().getNickname()).
                build();
        this.title = study.getTitle();
        this.studyType = study.getStudyType();
        this.numberOfMembers = study.getNumberOfMembers();
        this.studyInfo = study.getStudyInfo();
        this.studyStatus = study.getStudyStatus();
        this.dateStudyStart = study.getDateStudyStart();
        this.dateStudyEnd = study.getDateStudyEnd();
    }
}
