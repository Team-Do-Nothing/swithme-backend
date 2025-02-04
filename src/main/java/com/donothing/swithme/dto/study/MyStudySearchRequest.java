package com.donothing.swithme.dto.study;

import com.donothing.swithme.common.PagingRequest;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MyStudySearchRequest extends PagingRequest {
    @ApiModelProperty(value = "로그인 한 사용자")
    @Hidden
    private Long memberId;
}
