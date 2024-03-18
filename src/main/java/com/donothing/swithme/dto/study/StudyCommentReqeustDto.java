package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.Comment;
import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.Study;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StudyCommentReqeustDto {
    @NotNull(message = "댓글 내용은 필수입니다.")
    private String comment;

    public Comment toEntity(Long memberId, Long studyId) {
        return Comment.builder()
                .member(new Member(memberId))
                .study(new Study(studyId))
                .comment(comment)
                .build();
    }
}
