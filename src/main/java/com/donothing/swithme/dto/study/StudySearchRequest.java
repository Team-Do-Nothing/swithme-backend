package com.donothing.swithme.dto.study;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
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
}
