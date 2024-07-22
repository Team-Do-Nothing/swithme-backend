package com.donothing.swithme.dto.study;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StudySearchRequest {
    @ApiModelProperty(value = "카테고리")
    private String category;

    @ApiModelProperty(value = "세부 카테고리")
    private String detailCategory;

    @ApiModelProperty(value = "지역(구)")
    private String gu;

    @ApiModelProperty(value = "지역(동)")
    private String dong;

    @ApiModelProperty(value = "지역(제목)")
    private String title;

    @ApiModelProperty(value = "로그인 한 사용자")
    @Hidden
    private Long memberId;
}
