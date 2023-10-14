package com.donothing.swithme.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberStudy {

    @Id @GeneratedValue
    private Long memberStudyId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "study_id")
    private Study study;
    private LocalDateTime dateOkay;
}
