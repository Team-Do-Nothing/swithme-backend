package com.donothing.swithme.domain;

import lombok.Builder;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Column(nullable = false)
    private ApproveStatus approveStatus; // 승인여부 [WAIT, APPROVE, DENY]

    private LocalDateTime dateOkay; // 스터디 참여승인일자

    @Builder
    public MemberStudy(Member member, Study study) {
        this.member = member;
        this.study = study;
        this.approveStatus = ApproveStatus.WAIT;
    }
}
