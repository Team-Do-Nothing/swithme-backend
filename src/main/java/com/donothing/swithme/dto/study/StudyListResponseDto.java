package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.StudyStatus;
import com.donothing.swithme.domain.StudyType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class StudyListResponseDto {
    private Member member; // 방장 id 가져오기 위한 컬럼

    private String title;

    private StudyType studyType; // ONLINE, OFFLINE

    private int numberOfMembers; // 목표 인원수

    private String studyInfo;

    private StudyStatus studyStatus; // CURR, COMP, END

    private LocalDateTime dateStudyStart;

    private LocalDateTime dateStudyEnd;
}
