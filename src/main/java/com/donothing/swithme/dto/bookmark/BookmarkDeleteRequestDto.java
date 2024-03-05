package com.donothing.swithme.dto.bookmark;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description = "북마크 삭제 VO")
public class BookmarkDeleteRequestDto {
    @ApiModelProperty(value = "북마크 아이디", required = true)
    @NotNull(message = "삭제할 북마크 아이디는 필수입니다.")
    private Long bookmarkId;
}
