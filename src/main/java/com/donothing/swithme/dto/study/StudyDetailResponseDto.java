package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.domain.StudyStatus;
import com.donothing.swithme.domain.StudyType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class StudyDetailResponseDto {
    private Long studyId;
    private Member member;
    private String title;
    private StudyType studyType; // ONLINE, OFFLINE

    private int numberOfMembers; // 목표 인원수

    private String studyInfo;

    private StudyStatus studyStatus; // CURR, COMP, END

    private LocalDateTime dateStudyStart;

    private LocalDateTime dateStudyEnd;

    public StudyDetailResponseDto(Study study) {
        this.studyId = study.getStudyId();
//        this.member = study.getMember();
        this.title = study.getTitle();
        this.studyType = study.getStudyType();
        this.numberOfMembers = study.getNumberOfMembers();
        this.studyInfo = study.getStudyInfo();
        this.studyStatus = study.getStudyStatus();
        this.dateStudyStart = study.getDateStudyStart();
        this.dateStudyEnd = study.getDateStudyEnd();
    }
}
