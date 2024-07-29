package com.donothing.swithme.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ChallengeLog extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_challenge_id")
    private MemberChallenge memberChallenge;

    @Column(nullable = false)
    private String s3Url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChallengeLogStatus challengeLogStatus; // ACTIVE, INACTIVE

    public void setChallengeLogStatus(ChallengeLogStatus challengeLogStatus) {
        this.challengeLogStatus = challengeLogStatus;
    }
}
