package com.donothing.swithme.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ChallengeLog extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Column(nullable = false)
    private String s3Url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChallengeLogStatus challengeLogStatus; // ACTIVE, INACTIVE

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApproveStatus challengeLogApproveStatus; // 승인여부 [WAIT, APPROVE, DENY]

    public void setChallengeLogStatus(ChallengeLogStatus challengeLogStatus) {
        this.challengeLogStatus = challengeLogStatus;
    }
}
