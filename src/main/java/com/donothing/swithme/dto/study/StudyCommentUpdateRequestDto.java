package com.donothing.swithme.dto.study;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyCommentUpdateRequestDto {
    @NotNull(message = "댓글 id는 필수입니다.")
    private Long commentId;

    @NotNull(message = "댓글 내용은 필수입니다.")
    @ApiModelProperty(value = "댓글내용", required = true)
    private String comment;
}
