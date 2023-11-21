package com.donothing.swithme.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class ChallengeLog extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chalLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_chal_id")
    private MemberChallenge memberChallenge;

    private String s3Url;
}
