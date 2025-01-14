package com.donothing.swithme.dto.bookmark;

import com.donothing.swithme.common.PagingRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BookmarkSearchRequest extends PagingRequest {

    @ApiModelProperty(value = "로그인 한 사용자")
    private Long memberId;
}
