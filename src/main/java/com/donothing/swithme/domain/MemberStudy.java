package com.donothing.swithme.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class MemberStudy {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberStudyId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "study_id")
    private Study study;

    private LocalDateTime dateOkay; // 스터디 참여승인일자
}
