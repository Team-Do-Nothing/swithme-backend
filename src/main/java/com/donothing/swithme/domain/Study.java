package com.donothing.swithme.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Study {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

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

    private LocalDateTime dateStudyStart;

    private LocalDateTime dateStudyEnd;
}
