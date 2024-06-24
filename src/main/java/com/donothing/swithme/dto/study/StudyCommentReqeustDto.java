package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.Comment;
import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.Study;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyCommentReqeustDto {

    @ApiModelProperty(value = "대댓글 작성 시 원 댓글 아이디, 본댓글이면 null로 셋팅", example = "1")
    private Long recommentId;

    @NotNull(message = "댓글 내용은 필수입니다.")
    @ApiModelProperty(value = "댓글내용", required = true)
    private String comment;

    @ApiModelProperty(hidden = true)
    private Long memberId;

    public Comment toEntity(Long memberId, Long studyId) {
        return Comment.builder()
                .member(new Member(memberId))
                .deletedFlag(false)
                .study(new Study(studyId))
                .commentTag(recommentId)
                .comment(comment)
                .build();
    }
}
