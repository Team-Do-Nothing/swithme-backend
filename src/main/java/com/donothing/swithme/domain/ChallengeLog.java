package com.donothing.swithme.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class ChallengeLog {
    @Id @GeneratedValue
    private Long chalLogId;
//    private Member member;
    private LocalDateTime dateCreated;
    private String s3Url;
}
