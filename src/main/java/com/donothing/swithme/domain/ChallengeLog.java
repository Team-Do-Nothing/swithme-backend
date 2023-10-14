package com.donothing.swithme.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChallengeLog extends BaseEntity {

    @Id @GeneratedValue
    private Long chalLogId;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String s3Url;
}
