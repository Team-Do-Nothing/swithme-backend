package com.donothing.swithme.dto.study;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class StudyCommentReqeustDto {
    @NotNull(message = "댓글 내용은 필수입니다.")
    private String comment;
}
