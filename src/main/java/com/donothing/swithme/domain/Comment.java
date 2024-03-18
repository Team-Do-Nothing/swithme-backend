package com.donothing.swithme.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 방장 id 가져오기 위한 컬럼

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_id")
    private Study study;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String comment;

    private Long commentTag;

    private boolean deletedFlag;

    @Builder
    public Comment(Member member, Study study, String comment, Long commentTag, boolean deletedFlag) {
        this.member = member;
        this.study = study;
        this.comment = comment;
        this.commentTag = commentTag;
        this.deletedFlag = deletedFlag;
    }
}
