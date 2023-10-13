package com.donothing.swithme.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
public class Study {
    @Id @GeneratedValue
    private Long studyId;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String title;
    @Enumerated(EnumType.STRING)
    private StudyType studyType;
    private int numberOfMembers;
//    private regionCode;
    private String studyInfo;
    private StudyStatus studyStatus;
    private LocalDateTime dateCreated;
    private LocalDateTime dateStudyStart;
    private LocalDateTime dateStudyEnd;
}
