package com.donothing.swithme.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberChallenge {

    @Id @GeneratedValue
    private Long memberChalId;
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private LocalDateTime dateCreated;

}