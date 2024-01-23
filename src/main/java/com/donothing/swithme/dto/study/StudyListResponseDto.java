package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.domain.StudyStatus;
import com.donothing.swithme.domain.StudyType;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudyListResponseDto {
    private Long memberId; // 방장 id 가져오기 위한 컬럼

    private String title;

    private StudyType studyType; // ONLINE, OFFLINE

    private int numberOfMembers; // 목표 인원수

    private String studyInfo;

    private StudyStatus studyStatus; // CURR, COMP, END

    private LocalDateTime dateStudyStart;

    private LocalDateTime dateStudyEnd;

    public Study toEntity() {
        return Study.builder()
                .member(new Member(memberId))
                .title(title)
                .studyType(studyType)
                .studyStatus(studyStatus)
                .numberOfMembers(numberOfMembers)
                .studyInfo(studyInfo)
                .build();
    }
}
