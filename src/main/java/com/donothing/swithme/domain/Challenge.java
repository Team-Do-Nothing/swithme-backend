package com.donothing.swithme.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Challenge {
    @Id
    @GeneratedValue
    private long challengeId;
    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private int challengeFee;
    private String checkFormat;
    @Enumerated(EnumType.STRING)
    private ChallengeStatus challengeStatus;
    private LocalDateTime dateCreated;
    private int challengeFeeAll;
}