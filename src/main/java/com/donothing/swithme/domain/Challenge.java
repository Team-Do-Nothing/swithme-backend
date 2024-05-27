package com.donothing.swithme.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Challenge extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long challengeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Column(nullable = false)
    private String title;

    @Column(length = 100, nullable = false)
    private String goal;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @Column(nullable = false)
    private int challengeFee;

    @Column(nullable = false)
    private String checkFormat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChallengeStatus challengeStatus; // DAILY, SPECIAL

    @Column(nullable = false)
    private int challengeFeeAll;

    @Builder
    public Challenge(String title, String goal, String startDate, String endDate,
            int challengeFee, String checkFormat, ChallengeStatus challengeStatus) {
        this.title = title;
        this.goal = goal;
        this.startDate = startDate;
        this.endDate = endDate;
        this.challengeFee = challengeFee;
        this.checkFormat = checkFormat;
        this.challengeStatus = challengeStatus;
    }

    public Challenge(Long challengeId) {
        this.challengeId = challengeId;
    }
}
