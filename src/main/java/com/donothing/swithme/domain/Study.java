package com.donothing.swithme.domain;

import com.donothing.swithme.dto.study.StudyUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
@Entity
public class Study {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 방장 id 가져오기 위한 컬럼

    @Column(nullable = false, length = 100)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StudyType studyType; // ONLINE, OFFLINE

    @Column(nullable = false)
    private int numberOfMembers; // 목표 인원수

//    private regionCode; // 지역코드

    @Column(columnDefinition = "TEXT", length = 1000)
    private String studyInfo;

    @Column(nullable = false)
    private StudyStatus studyStatus; // CURR, COMP, END

    private String dateStudyStart;

    private String dateStudyEnd;

    @Builder
    public Study(Member member, String title, StudyType studyType, int numberOfMembers, String studyInfo,
            StudyStatus studyStatus, String dateStudyStart, String dateStudyEnd) {
        this.member = member;
        this.title = title;
        this.studyType = studyType;
        this.numberOfMembers = numberOfMembers;
        this.studyInfo = studyInfo;
        this.studyStatus = studyStatus;
        this.dateStudyStart = dateStudyStart;
        this.dateStudyEnd = dateStudyEnd;
    }

    public void update(StudyUpdateRequestDto request) {
        this.title = request.getTitle();
        this.studyType = request.getStudyType();
        this.numberOfMembers = request.getNumberOfMembers();
        this.studyInfo = request.getStudyInfo();
        this.studyStatus = request.getStudyStatus();
        this.dateStudyStart = request.getDateStudyStart();
        this.dateStudyEnd = request.getDateStudyEnd();
    }

    public Study(Long studyId) { this.studyId = studyId; }
}
